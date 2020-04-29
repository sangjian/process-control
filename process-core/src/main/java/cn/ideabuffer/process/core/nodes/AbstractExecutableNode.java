package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.LifecycleState;
import cn.ideabuffer.process.core.ProcessListener;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.exception.ProcessException;
import cn.ideabuffer.process.core.executor.NodeExecutors;
import cn.ideabuffer.process.core.rule.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public abstract class AbstractExecutableNode<R> extends AbstractNode implements ExecutableNode<R> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private boolean parallel = false;
    private Rule rule;
    private Executor executor;
    private List<ProcessListener> listeners;

    public AbstractExecutableNode() {
        this(false);
    }

    public AbstractExecutableNode(boolean parallel) {
        this(parallel, null);
    }

    public AbstractExecutableNode(Rule rule) {
        this(false, rule, null);
    }

    public AbstractExecutableNode(boolean parallel, Executor executor) {
        this(parallel, null, executor);
    }

    public AbstractExecutableNode(boolean parallel, Rule rule, Executor executor) {
        this(parallel, rule, executor, null);
    }

    public AbstractExecutableNode(boolean parallel, Rule rule, Executor executor, List<ProcessListener> listeners) {
        this.parallel = parallel;
        this.rule = rule;
        this.executor = executor;
        this.listeners = listeners;
    }

    public boolean isParallel() {
        return parallel;
    }

    public void setParallel(boolean parallel) {
        this.parallel = parallel;
    }

    @Override
    public void parallel() {
        this.parallel = true;
    }

    @Override
    public void parallel(Executor executor) {
        this.parallel = true;
        this.executor = executor;
    }

    @Override
    public void processOn(Rule rule) {
        this.rule = rule;
    }

    @Override
    public void addListeners(@NotNull ProcessListener... listeners) {
        this.listeners = Arrays.asList(listeners);
    }

    @Override
    public Rule getRule() {
        return this.rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    @Override
    public List<ProcessListener> getListeners() {
        return listeners;
    }

    public void setListeners(List<ProcessListener> listeners) {
        this.listeners = listeners;
    }

    protected boolean ruleCheck(@NotNull Context context) {
        return rule == null || rule.match(context);
    }

    protected void preExecute(@NotNull Context context) {

    }

    @NotNull
    @Override
    public ProcessStatus execute(Context context) throws Exception {
        if (!ruleCheck(context)) {
            return ProcessStatus.PROCEED;
        }

        if (parallel) {
            doParallelExecute(context);
            return ProcessStatus.PROCEED;
        }
        ProcessStatus status;
        try {
            preExecute(context);
            R result = doExecute(context);
            status = onComplete(context, result);
            notifyListeners(context, null, true);
        } catch (Exception e) {
            notifyListeners(context, e, false);
            status = onFailure(context, e);
        }
        return status;
    }

    private void doParallelExecute(Context context) {
        Executor e = executor == null ? NodeExecutors.DEFAULT_POOL : executor;
        e.execute(() -> {
            try {
                preExecute(context);
                R result = doExecute(context);
                onComplete(context, result);
                notifyListeners(context, null, true);
            } catch (Exception ex) {
                logger.error("doParallelExecute error, node:{}", this, ex);
                notifyListeners(context, ex, false);
                onFailure(context, ex);
            }

        });
    }

    private void notifyListeners(Context context, Exception e, boolean success) {
        if (listeners == null) {
            return;
        }
        try {
            if (success) {
                listeners.forEach(processListener -> processListener.onComplete(context));
            } else {
                listeners.forEach(processListener -> processListener.onFailure(context, e));
            }
        } catch (Exception exception) {
            // ignore
        }

    }

    /**
     * 子类实现具体执行逻辑
     *
     * @param context 当前流程上下文
     * @return 状态
     * @throws Exception
     * @see ProcessStatus
     */
    protected abstract R doExecute(Context context) throws Exception;

    @Override
    public ProcessStatus onComplete(@NotNull Context context, R result) {
        if (result instanceof ProcessStatus) {
            return (ProcessStatus)result;
        }
        return ProcessStatus.PROCEED;
    }

    @Override
    public ProcessStatus onFailure(@NotNull Context context, Throwable t) {
        throw new ProcessException(t);
    }

    @Override
    public Executor getExecutor() {
        return executor;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    @Override
    public final void destroy() {
        if (getState() != LifecycleState.INITIALIZED) {
            return;
        }
        synchronized (this) {
            if (getState() != LifecycleState.INITIALIZED) {
                return;
            }
            try {
                setState(LifecycleState.DESTROYING);
                if (executor instanceof ExecutorService) {
                    if (!((ExecutorService)executor).isShutdown()) {
                        ((ExecutorService)executor).shutdown();
                    }
                }
                onDestroy();
                setState(LifecycleState.DESTROYED);
            } catch (Throwable t) {
                handleException(t, "destroy failed!");
            }
        }
    }

}

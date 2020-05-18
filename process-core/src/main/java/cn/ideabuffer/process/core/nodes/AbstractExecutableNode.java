package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.LifecycleState;
import cn.ideabuffer.process.core.NodeListener;
import cn.ideabuffer.process.core.ProcessListener;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.exception.ProcessException;
import cn.ideabuffer.process.core.executor.NodeExecutors;
import cn.ideabuffer.process.core.rule.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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
public abstract class AbstractExecutableNode<R, P extends Processor<R>> extends AbstractNode
    implements ExecutableNode<R, P> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private boolean parallel = false;
    private Rule rule;
    private Executor executor;
    private P processor;
    private NodeListener<R> nodeListener;
    private List<ProcessListener<R>> listeners;

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
        this(parallel, rule, executor, null, null, null);
    }

    public AbstractExecutableNode(boolean parallel, Rule rule, Executor executor, List<ProcessListener<R>> listeners,
        NodeListener<R> nodeListener, P processor) {
        this.parallel = parallel;
        this.rule = rule;
        this.executor = executor;
        this.listeners = listeners;
        this.nodeListener = nodeListener;
        this.processor = processor;
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
    public void registerNodeListener(NodeListener<R> listener) {
        this.nodeListener = listener;
    }

    @Override
    public void registerProcessor(P processor) {
        this.processor = processor;
    }

    @Override
    public void addProcessListeners(@NotNull ProcessListener<R>... listeners) {
        this.listeners = Arrays.asList(listeners);
    }

    @Override
    public NodeListener<R> getNodeListener() {
        return this.nodeListener;
    }

    @Override
    public P getProcessor() {
        return this.processor;
    }

    @Override
    public Rule getRule() {
        return this.rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    @Override
    public List<ProcessListener<R>> getListeners() {
        return listeners;
    }

    public void setListeners(List<ProcessListener<R>> listeners) {
        this.listeners = listeners;
    }

    protected boolean ruleCheck(@NotNull Context context) {
        return rule == null || rule.match(context);
    }

    @NotNull
    @Override
    public ProcessStatus execute(Context context) throws Exception {

        if (getProcessor() == null || !ruleCheck(context)) {
            return ProcessStatus.PROCEED;
        }

        if (parallel) {
            doParallelExecute(context);
            return ProcessStatus.PROCEED;
        }
        ProcessStatus status;
        try {
            R result = getProcessor().process(context);
            status = onComplete(context, result);
            notifyListeners(context, result, null, true);
        } catch (Exception e) {
            notifyListeners(context, null, e, false);
            status = onFailure(context, e);
        }
        return status;
    }

    private void doParallelExecute(Context context) {
        Executor e = executor == null ? NodeExecutors.DEFAULT_POOL : executor;
        e.execute(() -> {
            try {
                R result = getProcessor().process(context);
                onComplete(context, result);
                notifyListeners(context, result, null, true);
            } catch (Exception ex) {
                logger.error("doParallelExecute error, node:{}", this, ex);
                notifyListeners(context, null, ex, false);
                onFailure(context, ex);
            }
        });
    }

    protected ProcessStatus onComplete(Context context, R result) {
        if (getNodeListener() != null) {
            return getNodeListener().onComplete(context, result);
        }
        if (result instanceof ProcessStatus) {
            return (ProcessStatus)result;
        }
        return ProcessStatus.PROCEED;
    }

    protected ProcessStatus onFailure(Context context, Exception e) {
        if (nodeListener != null) {
            return nodeListener.onFailure(context, e);
        }
        throw new ProcessException(e);
    }

    protected void notifyListeners(Context context, @Nullable R result, Exception e, boolean success) {
        if (listeners == null) {
            return;
        }
        listeners.forEach(processListener -> {
            try {
                if (success) {
                    processListener.onComplete(context, result);
                } else {
                    processListener.onFailure(context, e);
                }
            } catch (Exception ex) {
                // ignore
            }
        });
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

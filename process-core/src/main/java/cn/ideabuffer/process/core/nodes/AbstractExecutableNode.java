package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.LifecycleState;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.executor.NodeExecutors;
import cn.ideabuffer.process.core.handler.ExceptionHandler;
import cn.ideabuffer.process.core.rule.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public abstract class AbstractExecutableNode extends AbstractNode implements ExecutableNode {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private boolean parallel = false;
    private Rule rule;
    private Executor executor;
    private ExceptionHandler handler;

    public AbstractExecutableNode() {
        this(false);
    }

    public AbstractExecutableNode(boolean parallel) {
        this(parallel, null);
    }

    public AbstractExecutableNode(Rule rule) {
        this(false, rule, null, null);
    }

    public AbstractExecutableNode(boolean parallel, Executor executor) {
        this(parallel, null, executor, null);
    }

    public AbstractExecutableNode(boolean parallel, Rule rule, Executor executor, ExceptionHandler handler) {
        this.parallel = parallel;
        this.rule = rule;
        this.executor = executor;
        this.handler = handler;
    }

    public void setHandler(ExceptionHandler handler) {
        this.handler = handler;
    }

    public boolean isParallel() {
        return parallel;
    }

    public void setParallel(boolean parallel) {
        this.parallel = parallel;
    }

    @Override
    public ExecutableNode parallel() {
        this.parallel = true;
        return this;
    }

    @Override
    public ExecutableNode parallel(Executor executor) {
        this.parallel = true;
        this.executor = executor;
        return this;
    }

    @Override
    public ExecutableNode processOn(Rule rule) {
        this.rule = rule;
        return this;
    }

    @Override
    public Rule getRule() {
        return this.rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    protected boolean ruleCheck(Context context) {
        return rule == null || rule.match(context);
    }

    protected void preExecute(Context context) {

    }

    protected void postExecute(Context context) {

    }

    protected void whenComplete(Context context, Exception e) {

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

        Exception exp = null;

        preExecute(context);
        try {
            ProcessStatus status = doExecute(context);
            postExecute(context);
            return status;
        } catch (Exception e) {
            if (handler != null) {
                handler.handle(e);
            } else {
                exp = e;
                throw e;
            }
        } finally {
            whenComplete(context, exp);
        }

        return ProcessStatus.PROCEED;
    }

    private void doParallelExecute(Context context) {
        Executor e = executor == null ? NodeExecutors.DEFAULT_POOL : executor;
        e.execute(() -> {
            Exception exp = null;
            preExecute(context);
            try {
                doExecute(context);
                postExecute(context);
            } catch (Exception ex) {
                if (handler != null) {
                    handler.handle(ex);
                } else {
                    logger.error("doParallelExecute error, node:{}", this, ex);
                    exp = ex;
                    throw new RuntimeException(ex);
                }
            } finally {
                whenComplete(context, exp);
            }
        });
    }

    /**
     * 子类实现具体执行逻辑
     *
     * @param context 当前流程上下文
     * @return 状态
     * @throws Exception
     * @see ProcessStatus
     */
    @NotNull
    protected abstract ProcessStatus doExecute(Context context) throws Exception;

    @Override
    public ExecutableNode exceptionHandler(ExceptionHandler handler) {
        this.handler = handler;
        return this;
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        return this.handler;
    }

    @Override
    public Executor getExecutor() {
        return executor;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    @Override
    public void destroy() {
        if (getState() == LifecycleState.DESTROYING || getState() == LifecycleState.DESTROYED) {
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

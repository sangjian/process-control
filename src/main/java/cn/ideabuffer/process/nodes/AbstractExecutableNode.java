package cn.ideabuffer.process.nodes;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.Executable;
import cn.ideabuffer.process.handler.ExceptionHandler;
import cn.ideabuffer.process.rule.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;

import static cn.ideabuffer.process.executor.NodeExecutors.DEFAULT_POOL;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public abstract class AbstractExecutableNode extends AbstractNode implements ExecutableNode {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected boolean parallel = false;
    protected Rule rule;
    protected Executor executor;
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

    public AbstractExecutableNode(boolean parallel, Rule rule, Executor executor,
        ExceptionHandler handler) {
        this.parallel = parallel;
        this.rule = rule;
        this.executor = executor;
        this.handler = handler;
    }

    public void setParallel(boolean parallel) {
        this.parallel = parallel;
    }

    public void setHandler(ExceptionHandler handler) {
        this.handler = handler;
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

    @Override
    public boolean execute(Context context) throws Exception {
        if (!ruleCheck(context)) {
            return false;
        }

        if (parallel) {
            doParallelExecute(context);
            return false;
        }

        Exception exp = null;

        preExecute(context);
        try {
            boolean result = doExecute(context);
            postExecute(context);
            return result;
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

        return false;
    }

    private void doParallelExecute(Context context) {
        Executor e = executor == null ? DEFAULT_POOL : executor;
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
     * @return <li>false: 继续执行整个实例的下游节点</li><li>true: 不再执行整个实例的下游节点</li>
     * @throws Exception
     * @see Executable#CONTINUE_PROCESSING
     * @see Executable#PROCESSING_COMPLETE
     */
    protected abstract boolean doExecute(Context context) throws Exception;

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

}

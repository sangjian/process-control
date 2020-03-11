package cn.ideabuffer.process.nodes;

import cn.ideabuffer.process.Context;
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
    }

    public AbstractExecutableNode(Rule rule) {
        this.rule = rule;
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

    @Override
    public boolean execute(Context context) throws Exception {
        if (!ruleCheck(context)) {
            return false;
        }
        preExecute(context);
        Executor e = null;

        if (parallel && executor == null) {
            e = DEFAULT_POOL;
        } else if (executor != null) {
            e = executor;
        }

        Runnable task = () -> {
            try {
                doExecute(context);
            } catch (Exception ex) {
                if(handler != null) {
                    handler.handle(ex);
                } else {
                    logger.error("execute error, node:{}", this, ex);
                    throw new RuntimeException(ex);
                }
            }
        };

        if (e != null) {
            e.execute(task);
        } else {
            task.run();
        }

        return false;
    }

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

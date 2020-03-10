package cn.ideabuffer.process.nodes;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.handler.ExceptionHandler;
import cn.ideabuffer.process.rule.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;

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

    protected static final Executor DEFAULT_POOL = new ThreadPerTaskExecutor();

    static final class ThreadPerTaskExecutor implements Executor {

        @Override
        public void execute(Runnable r) {
            new Thread(r).start();
        }
    }

    public AbstractExecutableNode() {
    }

    public AbstractExecutableNode(Rule rule) {
        this.rule = rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public void setParallel(boolean parallel) {
        this.parallel = parallel;
    }

    public void setHandler(ExceptionHandler handler) {
        this.handler = handler;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
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

    protected boolean ruleCheck(Context context) {
        return rule == null || rule.match(context);
    }

    protected void preExecute(Context context) {

    }

    @Override
    public boolean execute(Context context) throws Exception {
        if(!ruleCheck(context)) {
            return false;
        }
        preExecute(context);
        if(parallel && executor == null) {
            DEFAULT_POOL.execute(new NodeTask(context));
        } else if(executor != null) {
            executor.execute(new NodeTask(context));
        } else {
            try {
                return doExecute(context);
            } catch (Exception e) {
                if(handler != null) {
                    return handler.handle(e);
                }
            }

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

    class NodeTask implements Runnable{
        Context context;

        NodeTask(Context context) {
            this.context = context;
        }

        @Override
        public void run() {
            try {
                doExecute(context);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}

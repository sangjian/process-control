package cn.ideabuffer.process.nodes;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.ExecutableNode;

import java.util.concurrent.*;
import java.util.concurrent.ExecutorService;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public abstract class AbstractExecutableNode extends AbstractNode implements ExecutableNode {

    private boolean parallel = false;

    private ExecutorService executor;

    private static final Executor DEFAULT_POOL = new ThreadPerTaskExecutor();

    static final class ThreadPerTaskExecutor implements Executor {

        @Override
        public void execute(Runnable r) {
            new Thread(r).start();
        }
    }

    public AbstractExecutableNode() {
    }

    public AbstractExecutableNode(String id) {
        super(id);
    }

    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    @Override
    public ExecutableNode parallel() {
        this.parallel = true;
        return this;
    }

    @Override
    public ExecutableNode parallel(ExecutorService executor) {
        this.parallel = true;
        this.executor = executor;
        return this;
    }

    @Override
    public boolean execute(Context context) throws Exception {
        if(parallel && executor == null) {
            DEFAULT_POOL.execute(new NodeTask(context));
        } else if(executor != null) {
            executor.execute(new NodeTask(context));
        } else {
            return doExecute(context);
        }
        return false;
    }

    protected abstract boolean doExecute(Context context) throws Exception;

    @Override
    public ExecutorService getExecutor() {
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

    class ExecutableNodeFacade implements ExecutableNode {

        private AbstractExecutableNode node;

        ExecutableNodeFacade(AbstractExecutableNode node) {
            this.node = node;
        }

        @Override
        public boolean execute(Context context) throws Exception {return node.doExecute(context);}

        @Override
        public ExecutorService getExecutor() {return node.getExecutor();}

        @Override
        public ExecutableNode parallel() {
            throw new UnsupportedOperationException();
        }

        @Override
        public ExecutableNode parallel(ExecutorService executor) {
            throw new UnsupportedOperationException();
        }


        @Override
        public String getId() {return node.getId();}

        @Override
        public boolean enabled(Context context) {return node.enabled(context);}
    }
}

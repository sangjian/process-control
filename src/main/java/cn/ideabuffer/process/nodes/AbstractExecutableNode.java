package cn.ideabuffer.process.nodes;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.ExecutableNode;
import cn.ideabuffer.process.executor.ExecuteStrategies;
import cn.ideabuffer.process.executor.ExecuteStrategy;

import java.util.concurrent.ExecutorService;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public abstract class AbstractExecutableNode extends AbstractNode implements ExecutableNode {

    private ExecutorService executor;

    private ExecuteStrategy executeStrategy = ExecuteStrategies.SERIAL;

    public AbstractExecutableNode() {
    }

    public AbstractExecutableNode(String id) {
        super(id);
    }

    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    public void setExecuteStrategy(ExecuteStrategy executeStrategy) {
        this.executeStrategy = executeStrategy;
    }

    @Override
    public ExecutableNode executeOn(ExecutorService executor) {
        this.executor = executor;
        return this;
    }

    @Override
    public ExecutableNode executeOn(ExecutorService executor, ExecuteStrategy strategy) {
        this.executor = executor;
        if(strategy != null) {
            this.executeStrategy = strategy;
        }
        return this;
    }

    @Override
    public ExecuteStrategy getExecuteStrategy() {
        return executeStrategy;
    }

    @Override
    public boolean execute(Context context) throws Exception {
        ExecutableNodeFacade facade = new ExecutableNodeFacade(this);
        return executeStrategy.execute(executor, context, facade);
    }

    protected abstract boolean doExecute(Context context) throws Exception;

    @Override
    public ExecutorService getExecutor() {
        return executor;
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
        public ExecutableNode executeOn(ExecutorService executor) {return node.executeOn(executor);}

        @Override
        public ExecutableNode executeOn(ExecutorService executor, ExecuteStrategy strategy) {
            return node.executeOn(executor, strategy);
        }

        @Override
        public ExecuteStrategy getExecuteStrategy() {return node.getExecuteStrategy();}

        @Override
        public String getId() {return node.getId();}

        @Override
        public boolean enabled(Context context) {return node.enabled(context);}
    }
}

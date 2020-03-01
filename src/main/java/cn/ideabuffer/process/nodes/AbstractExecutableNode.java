package cn.ideabuffer.process.nodes;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.ExecutableNode;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public abstract class AbstractExecutableNode extends AbstractNode implements ExecutableNode {

    public AbstractExecutableNode() {
    }

    public AbstractExecutableNode(String id) {
        super(id);
    }

    protected ExecutorService executor;

    @Override
    public ExecutableNode executeOn(ExecutorService executor) {
        this.executor = executor;
        return this;
    }

    @Override
    public ExecutorService getExecutor() {
        return executor;
    }

    class NodeTask implements Callable<Boolean>{
        ExecutableNode node;
        Context context;

        NodeTask(ExecutableNode node, Context context) {
            this.node = node;
            this.context = context;
        }

        @Override
        public Boolean call() throws Exception {
            return node.execute(context);
        }
    }
}

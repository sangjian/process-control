package cn.ideabuffer.process.group;

import cn.ideabuffer.process.AbstractExecutableNode;
import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.ContextWrapper;
import cn.ideabuffer.process.ExecutableNode;
import cn.ideabuffer.process.block.Block;
import cn.ideabuffer.process.executor.ExecuteStrategies;
import cn.ideabuffer.process.executor.ExecuteStrategy;

import java.util.concurrent.ExecutorService;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public abstract class AbstractExecutableNodeGroup extends AbstractExecutableNode implements ExecutableNodeGroup {

    private ExecutableNode[] nodes = new ExecutableNode[0];

    private ExecuteStrategy executeStrategy = ExecuteStrategies.SERIAL;

    public AbstractExecutableNodeGroup() {
        this(null);
    }

    public AbstractExecutableNodeGroup(ExecutableNode... nodes) {
        this(null, nodes);
    }

    public AbstractExecutableNodeGroup(String id, ExecutableNode... nodes) {
        super(id);
        if(nodes != null) {
            this.nodes = nodes;
        }
    }

    public void setNodes(ExecutableNode[] nodes) {
        this.nodes = nodes;
    }

    public void setExecuteStrategy(ExecuteStrategy executeStrategy) {
        this.executeStrategy = executeStrategy;
    }

    @Override
    public ExecutableNodeGroup executeOn(ExecutorService executor) {
        this.executeOn(executor, ExecuteStrategies.SERIAL);
        return this;
    }

    @Override
    public ExecutableNodeGroup executeOn(ExecutorService executor, ExecuteStrategy strategy) {
        super.executeOn(executor);
        if(strategy == null) {
            throw new NullPointerException();
        }
        this.executeStrategy = strategy;
        return this;
    }

    @Override
    public ExecuteStrategy getExecuteStrategy() {
        return executeStrategy;
    }

    @Override
    public ExecutableNode[] getNodes() {
        return nodes;
    }

    @Override
    public ExecutableNodeGroup addNode(ExecutableNode node) {
        if(node == null) {
            throw  new NullPointerException();
        }
        ExecutableNode[] newArr = new ExecutableNode[nodes.length + 1];
        System.arraycopy(nodes, 0, newArr, 0, nodes.length);
        newArr[nodes.length] = node;
        this.nodes = newArr;
        return this;
    }

    @Override
    public boolean execute(Context context) throws Exception {
        try {
            Block groupBlock = new Block(context.getBlock());
            ContextWrapper contextWrapper = new ContextWrapper(context, groupBlock);
            if(executeStrategy.execute(executor, contextWrapper, nodes)) {
                return true;
            }
        } catch (Exception e) {
            throw e;
        }

        return false;
    }

}

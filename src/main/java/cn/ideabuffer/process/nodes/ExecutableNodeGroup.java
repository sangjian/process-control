package cn.ideabuffer.process.nodes;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.ContextWrapper;
import cn.ideabuffer.process.ExecutableNode;
import cn.ideabuffer.process.block.Block;
import cn.ideabuffer.process.branch.DefaultBranch;
import cn.ideabuffer.process.executor.ExecuteStrategy;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @author sangjian.sj
 * @date 2020/03/01
 */
public class ExecutableNodeGroup extends DefaultBranch {

    public ExecutableNodeGroup() {
        this(null, null);
    }

    public ExecutableNodeGroup(String id) {
        this(id, null);
    }

    public ExecutableNodeGroup(List<ExecutableNode> nodes) {
        this(null, nodes);
    }

    public ExecutableNodeGroup(String id, List<ExecutableNode> nodes) {
        super(id, nodes);
    }

    @Override
    public ExecutableNodeGroup addNodes(ExecutableNode... nodes) {
        super.addNodes(nodes);
        return this;
    }

    @Override
    public ExecutableNodeGroup executeOn(ExecutorService executor) {
        super.executeOn(executor);
        return this;
    }

    @Override
    public ExecutableNodeGroup executeOn(ExecutorService executor, ExecuteStrategy strategy) {
        super.executeOn(executor, strategy);
        return this;
    }

    @Override
    public boolean execute(Context context) throws Exception {
        Block block = new Block(context.getBlock());
        ContextWrapper contextWrapper = new ContextWrapper(context, block);
        return super.execute(contextWrapper);
    }
}

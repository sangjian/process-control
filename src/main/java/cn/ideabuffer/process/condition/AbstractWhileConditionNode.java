package cn.ideabuffer.process.condition;

import cn.ideabuffer.process.AbstractExecutableNode;
import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.ContextWrapper;
import cn.ideabuffer.process.ExecutableNode;
import cn.ideabuffer.process.block.Block;
import cn.ideabuffer.process.block.BlockWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public abstract class AbstractWhileConditionNode extends AbstractExecutableNode implements WhileConditionNode {

    private List<ExecutableNode> nodes;

    public AbstractWhileConditionNode(String id) {
        super(id);
        nodes = new ArrayList<>();
    }

    public void setNodes(List<ExecutableNode> nodes) {
        this.nodes = nodes;
    }

    @Override
    public WhileConditionNode addNode(ExecutableNode node) {
        if (node == null) {
            throw new NullPointerException();
        }
        nodes.add(node);
        return this;
    }

    @Override
    public List<ExecutableNode> getNodes() {
        return nodes;
    }

    @Override
    public boolean execute(Context context) throws Exception {
        List<ExecutableNode> list = getNodes();
        if (list == null || list.size() == 0) {
            return false;
        }

        Block whileBlock = new Block(true, true, context.getBlock());
        BlockWrapper blockWrapper = new BlockWrapper(whileBlock);
        ContextWrapper whileContext = new ContextWrapper(context, whileBlock);
        while (true) {
            Boolean judgement = judge(whileContext);
            if (!Boolean.TRUE.equals(judgement)) {
                break;
            }
            blockWrapper.resetBreak();
            blockWrapper.resetContinue();
            if(executeNodes(list, whileContext, blockWrapper)) {
                return true;
            }
            if (blockWrapper.hasBroken()) {
                break;
            }
        }

        return false;
    }

    protected boolean executeNodes(List<ExecutableNode> list, Context context, BlockWrapper blockWrapper)
        throws Exception {
        for (ExecutableNode node : list) {
            boolean stop = node.execute(context);
            if (stop) {
                return true;
            }
            if (blockWrapper.hasBroken()) {
                break;
            }
            if (blockWrapper.hasContinued()) {
                break;
            }
        }
        return false;
    }
}

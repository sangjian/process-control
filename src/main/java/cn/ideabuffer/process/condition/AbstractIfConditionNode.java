package cn.ideabuffer.process.condition;

import cn.ideabuffer.process.nodes.AbstractExecutableNode;
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
public abstract class AbstractIfConditionNode<E> extends AbstractExecutableNode implements IfConditionNode<E> {

    private List<ExecutableNode> trueNodes;

    private List<ExecutableNode> falseNodes;

    public AbstractIfConditionNode(String id) {
        super(id);
        trueNodes = new ArrayList<>();
        falseNodes = new ArrayList<>();
    }

    public void setTrueNodes(List<ExecutableNode> trueNodes) {
        this.trueNodes = trueNodes;
    }

    public void setFalseNodes(List<ExecutableNode> falseNodes) {
        this.falseNodes = falseNodes;
    }

    @Override
    public IfConditionNode<E> addTrueNode(ExecutableNode node) {
        if(node == null) {
            throw new NullPointerException();
        }
        trueNodes.add(node);
        return this;
    }

    @Override
    public IfConditionNode<E> addFalseNode(ExecutableNode node) {
        if(node == null) {
            throw new NullPointerException();
        }
        falseNodes.add(node);
        return this;
    }

    @Override
    public List<ExecutableNode> getTrueNodes() {
        return trueNodes;
    }

    @Override
    public List<ExecutableNode> getFalseNodes() {
        return falseNodes;
    }

    @Override
    public boolean execute(Context context) throws Exception {

        Boolean judgement = judge(context);
        List<ExecutableNode> nodeList;
        if (Boolean.TRUE.equals(judgement)) {
            nodeList = trueNodes;
        } else {
            nodeList = falseNodes;
        }
        if(nodeList == null || nodeList.size() == 0) {
            return false;
        }
        Block ifBlock = new Block(context.getBlock());
        BlockWrapper blockWrapper = new BlockWrapper(ifBlock);
        ContextWrapper contextWrapper = new ContextWrapper(context, ifBlock);
        for (ExecutableNode node : nodeList) {
            boolean stop = node.execute(contextWrapper);
            if(stop) {
                return true;
            }
            if(blockWrapper.hasBroken() || blockWrapper.hasContinued()) {
                break;
            }
        }
        return false;

    }
}

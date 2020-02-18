package cn.ideabuffer.process;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public abstract class AbstractIfConditionNode<E> extends AbstractNode implements IfConditionNode<E> {

    private List<ExecutableNode> trueNodeList;

    private List<ExecutableNode> falseNodeList;

    public AbstractIfConditionNode(String id) {
        super(id);
        trueNodeList = new ArrayList<>();
        falseNodeList = new ArrayList<>();
    }

    @Override
    public IfConditionNode<E> addTrueNode(ExecutableNode node) {
        if(node == null) {
            throw new NullPointerException();
        }
        trueNodeList.add(node);
        return this;
    }

    @Override
    public IfConditionNode<E> addFalseNode(ExecutableNode node) {
        if(node == null) {
            throw new NullPointerException();
        }
        falseNodeList.add(node);
        return this;
    }

    @Override
    public List<ExecutableNode> getTrueNodes() {
        return trueNodeList;
    }

    @Override
    public List<ExecutableNode> getFalseNodes() {
        return falseNodeList;
    }

    @Override
    public boolean execute(Context context) throws Exception {

        Boolean judgement = judge(context);
        List<ExecutableNode> nodeList;
        if (Boolean.TRUE.equals(judgement)) {
            nodeList = trueNodeList;
        } else {
            nodeList = falseNodeList;
        }
        Block ifBlock = new DefaultBlock(context.getBlock());
        ContextWrapper contextWrapper = new ContextWrapper(context, ifBlock);
        for (ExecutableNode node : nodeList) {
            boolean stop = node.execute(contextWrapper);
            if(stop) {
                return true;
            }
            if(ifBlock.hasBroken() || ifBlock.hasContinued()) {
                break;
            }
        }
        return false;

    }
}

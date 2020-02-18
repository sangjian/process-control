package cn.ideabuffer.process;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public abstract class AbstractSwitchConditionNode<V> extends AbstractNode implements SwitchConditionNode<V> {

    private List<ExpectableNode<V>> nodes;

    private ExecutableNode defaultNode;

    public AbstractSwitchConditionNode(String id) {
        super(id);
        nodes = new ArrayList<>();
    }

    @Override
    public SwitchConditionNode<V> switchCase(ExpectableNode<V> node) {
        if(node == null) {
            throw new NullPointerException();
        }
        nodes.add(node);
        return this;
    }

    @Override
    public SwitchConditionNode<V> defaultCase(ExecutableNode node) {
        this.defaultNode = node;
        return this;
    }

    @Override
    public ExecutableNode getDefaultNode() {
        return this.defaultNode;
    }

    @Override
    public List<ExpectableNode<V>> getCaseNodes() {
        return nodes;
    }

    @Override
    public boolean execute(Context context) throws Exception {
        List<ExpectableNode<V>> list = getCaseNodes();
        if(list == null || list.size() == 0) {
            return false;
        }
        V judgement = judge(context);

        Block switchBlock = new DefaultBlock(true, false, context.getBlock());
        ContextWrapper switchContext = new ContextWrapper(context, switchBlock);
        boolean hasBroken = false;
        for (ExpectableNode<V> node : list) {
            V expectation = node.expectation();
            if(judgement == null && expectation != null) {
                continue;
            }
            if(judgement != null && !judgement.equals(expectation)) {
                continue;
            }
            boolean stop = node.execute(switchContext);
            if(stop) {
                return true;
            }
            if(switchBlock.hasBroken() || switchBlock.hasContinued()) {
                hasBroken = true;
                break;
            }

        }
        if(!hasBroken) {
            return defaultNode.execute(switchContext);
        }
        return false;
    }
}

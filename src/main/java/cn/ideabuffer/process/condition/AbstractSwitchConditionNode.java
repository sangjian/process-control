package cn.ideabuffer.process.condition;

import cn.ideabuffer.process.*;
import cn.ideabuffer.process.block.Block;
import cn.ideabuffer.process.block.BlockWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public abstract class AbstractSwitchConditionNode<V> extends AbstractExecutableNode implements SwitchConditionNode<V> {

    private List<ExpectableNode<V>> caseNodes;

    private List<ExecutableNode> defaultNodes;

    public AbstractSwitchConditionNode() {
        this(null);
    }

    public AbstractSwitchConditionNode(String id) {
        this(id, null);
    }

    public AbstractSwitchConditionNode(String id, List<ExpectableNode<V>> caseNodes) {
        this(id, caseNodes, null);
    }

    public AbstractSwitchConditionNode(String id, List<ExpectableNode<V>> caseNodes,
        List<ExecutableNode> defaultNodes) {
        super(id);
        this.caseNodes = caseNodes == null ? new ArrayList<>() : caseNodes;
        this.defaultNodes = defaultNodes == null ? new ArrayList<>() : defaultNodes;
    }

    public void setCaseNodes(List<ExpectableNode<V>> caseNodes) {
        this.caseNodes = caseNodes;
    }

    public void setDefaultNodes(List<ExecutableNode> defaultNodes) {
        this.defaultNodes = defaultNodes;
    }

    @Override
    public SwitchConditionNode<V> switchCase(ExpectableNode<V> node) {
        if(node == null) {
            throw new NullPointerException();
        }
        caseNodes.add(node);
        return this;
    }

    @Override
    public SwitchConditionNode<V> defaultCase(ExecutableNode node) {
        this.defaultNodes.add(node);
        return this;
    }

    @Override
    public List<ExecutableNode> getDefaultNodes() {
        return defaultNodes;
    }

    @Override
    public List<ExpectableNode<V>> getCaseNodes() {
        return caseNodes;
    }

    @Override
    public boolean execute(Context context) throws Exception {
        List<ExpectableNode<V>> list = getCaseNodes();
        if(list == null || list.size() == 0) {
            return false;
        }
        V judgement = judge(context);

        Block switchBlock = new Block(true, false, context.getBlock());
        BlockWrapper blockWrapper = new BlockWrapper(switchBlock);
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
            if(blockWrapper.hasBroken() || blockWrapper.hasContinued()) {
                hasBroken = true;
                break;
            }

        }
        if(hasBroken) {
            return false;
        }
        if(defaultNodes == null || defaultNodes.size() == 0) {
            return false;
        }
        for (ExecutableNode node : defaultNodes) {
            if(node.execute(switchContext)) {
                return true;
            }
            if(blockWrapper.hasBroken() || blockWrapper.hasContinued()) {
                break;
            }
        }
        return false;
    }
}

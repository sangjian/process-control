package cn.ideabuffer.process.condition;

import cn.ideabuffer.process.*;
import cn.ideabuffer.process.block.Block;
import cn.ideabuffer.process.block.BlockFacade;
import cn.ideabuffer.process.block.BlockWrapper;
import cn.ideabuffer.process.block.DefaultBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public abstract class AbstractSwitchConditionNode<V> extends AbstractExecutableNode implements SwitchConditionNode<V> {

    private List<ExpectableNode<V>> nodes;

    private ExecutableNode defaultNode;

    public AbstractSwitchConditionNode() {
        this(null);
    }

    public AbstractSwitchConditionNode(String id) {
        this(id, new ArrayList<>());
    }

    public AbstractSwitchConditionNode(String id, List<ExpectableNode<V>> nodes) {
        this(id, nodes, null);
    }

    public AbstractSwitchConditionNode(String id, List<ExpectableNode<V>> nodes,
        ExecutableNode defaultNode) {
        super(id);
        if(nodes == null) {
            throw new NullPointerException();
        }
        this.nodes = nodes;
        this.defaultNode = defaultNode;
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
        BlockWrapper blockWrapper = new BlockWrapper(switchBlock);
        ContextWrapper switchContext = new ContextWrapper(context, new BlockFacade(blockWrapper));
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
        if(!hasBroken) {
            return defaultNode.execute(switchContext);
        }
        return false;
    }

    @Override
    public ExecutableNode executeOn(ExecutorService executor) {
        return null;
    }
}

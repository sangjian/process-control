package cn.ideabuffer.process;

import cn.ideabuffer.process.nodes.AggregatableNode;
import cn.ideabuffer.process.nodes.BaseNode;
import cn.ideabuffer.process.nodes.ExecutableNode;
import cn.ideabuffer.process.nodes.NodeGroup;
import cn.ideabuffer.process.nodes.branch.BranchNode;
import cn.ideabuffer.process.nodes.condition.DoWhileConditionNode;
import cn.ideabuffer.process.nodes.condition.IfConditionNode;
import cn.ideabuffer.process.nodes.condition.WhileConditionNode;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public class DefaultProcessDefine<R> implements ProcessDefine<R> {

    private Node[] nodes = new Node[0];

    private BaseNode<R> baseNode;

    private ProcessDefine<R> addNode(Node node) {
        if (node == null) {
            throw new IllegalArgumentException();
        }
        Node[] newArr = new Node[nodes.length + 1];
        System.arraycopy(nodes, 0, newArr, 0, nodes.length);
        newArr[nodes.length] = node;
        nodes = newArr;
        return this;
    }

    @Override
    public ProcessDefine<R> addProcessNode(@NotNull ExecutableNode node) {
        return addNode(node);
    }

    @Override
    public ProcessDefine<R> addIf(@NotNull IfConditionNode node) {
        return addNode(node);
    }

    @Override
    public ProcessDefine<R> addWhile(@NotNull WhileConditionNode node) {
        return addNode(node);
    }

    @Override
    public ProcessDefine<R> addDoWhile(@NotNull DoWhileConditionNode node) {
        return addNode(node);
    }

    @Override
    public ProcessDefine<R> addGroup(@NotNull NodeGroup group) {
        return addNode(group);
    }

    @Override
    public ProcessDefine<R> addAggregateNode(@NotNull AggregatableNode node) {
        return addNode(node);
    }

    @Override
    public ProcessDefine<R> addBranchNode(@NotNull BranchNode node) {
        return addNode(node);
    }

    @Override
    public ProcessDefine<R> addBaseNode(@NotNull BaseNode<R> node) {
        this.baseNode = node;
        return this;
    }

    public void setNodes(@NotNull Node[] nodes) {
        this.nodes = nodes;
    }

    public void setBaseNode(BaseNode<R> baseNode) {
        this.baseNode = baseNode;
    }

    @NotNull
    @Override
    public Node[] getNodes() {
        return nodes;
    }

    @Override
    public BaseNode<R> getBaseNode() {
        return baseNode;
    }
}
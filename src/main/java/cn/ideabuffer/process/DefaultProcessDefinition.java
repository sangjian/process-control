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
public class DefaultProcessDefinition<R> implements ProcessDefinition<R> {

    private Node[] nodes = new Node[0];

    private BaseNode<R> baseNode;

    private ProcessDefinition<R> addNode(Node node) {
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
    public ProcessDefinition<R> addProcessNode(@NotNull ExecutableNode node) {
        return addNode(node);
    }

    @Override
    public ProcessDefinition<R> addIf(@NotNull IfConditionNode node) {
        return addNode(node);
    }

    @Override
    public ProcessDefinition<R> addWhile(@NotNull WhileConditionNode node) {
        return addNode(node);
    }

    @Override
    public ProcessDefinition<R> addDoWhile(@NotNull DoWhileConditionNode node) {
        return addNode(node);
    }

    @Override
    public ProcessDefinition<R> addGroup(@NotNull NodeGroup group) {
        return addNode(group);
    }

    @Override
    public ProcessDefinition<R> addAggregateNode(@NotNull AggregatableNode node) {
        return addNode(node);
    }

    @Override
    public ProcessDefinition<R> addBranchNode(@NotNull BranchNode node) {
        return addNode(node);
    }

    @Override
    public ProcessDefinition<R> addBaseNode(@NotNull BaseNode<R> node) {
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
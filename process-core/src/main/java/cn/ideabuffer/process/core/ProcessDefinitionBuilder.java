package cn.ideabuffer.process.core;

import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.exceptions.IllegalResultKeyException;
import cn.ideabuffer.process.core.exceptions.UnregisteredKeyException;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.nodes.NodeGroup;
import cn.ideabuffer.process.core.nodes.aggregate.AggregatableNode;
import cn.ideabuffer.process.core.nodes.aggregate.DistributeAggregatableNode;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.nodes.condition.DoWhileConditionNode;
import cn.ideabuffer.process.core.nodes.condition.IfConditionNode;
import cn.ideabuffer.process.core.nodes.condition.WhileConditionNode;
import cn.ideabuffer.process.core.processors.wrapper.StatusWrapperHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ProcessDefinitionBuilder<R> implements Builder<ProcessDefinition<R>> {

    private InitializeMode initializeMode = InitializeMode.ON_REGISTER;

    private Node[] nodes = new Node[0];

    @Nullable
    private Key<R> resultKey;

    private ReturnCondition<R> returnCondition;

    private List<StatusWrapperHandler> handlers;

    private Set<Key<?>> declaringKeys;

    private boolean declaredRestrict;

    private ProcessDefinitionBuilder() {
    }

    public static <R> ProcessDefinitionBuilder<R> newBuilder() {
        return new ProcessDefinitionBuilder<>();
    }

    private void addNode(@NotNull Node... nodes) {
        if (nodes.length == 0) {
            return;
        }
//        // 检查key的注册
//        for (Node node : nodes) {
//            checkKeyRegistry(node);
//        }
//        returnableCheck(nodes);
        if (initializeMode == InitializeMode.ON_REGISTER) {
            // 初始化节点
            LifecycleManager.initialize(Arrays.asList(nodes));
        }

        int oldLen = this.nodes.length;
        int newLen = this.nodes.length + nodes.length;
        Node[] newArr = new Node[newLen];
        System.arraycopy(this.nodes, 0, newArr, 0, oldLen);
        System.arraycopy(nodes, 0, newArr, oldLen, nodes.length);

        this.nodes = newArr;
    }

    private void returnableCheck(Node... nodes) {
        for (Node node : nodes) {
            if (node instanceof ExecutableNode) {
                Key<?> nodeResultKey = ((ExecutableNode) node).getResultKey();
                if (((ExecutableNode) node).getReturnCondition() != null) {
                    // 没有设置结果key
                    if (nodeResultKey == null) {
                        throw new NullPointerException(String.format("resultKey must be set for node:%s.", node));
                    }
                    if (resultKey == null) {
                        throw new NullPointerException(
                                String.format("the resultKey:[%s] must be set for the definition.", nodeResultKey));
                    }
                    // 结果Key不一致
                    if (!nodeResultKey.equals(resultKey)) {
                        throw new IllegalResultKeyException(String
                                .format("resultKey[%s] of returnable node is not equals resultKey:[%s] of definition",
                                        nodeResultKey, resultKey));
                    }
                }
                // 没有返回条件，使用默认的返回条件
                if (nodeResultKey != null && nodeResultKey.equals(resultKey)
                        && ((ExecutableNode) node).getReturnCondition() == null) {
                    //noinspection unchecked
                    ((ExecutableNode) node).returnOn(returnCondition);
                }
            }
        }
    }

    private void checkKeyRegistry() {
        for (Node node : this.nodes) {
            checkKeyRegistry(node);
        }
    }

    private void checkKeyRegistry(Node node) {
        if (!this.declaredRestrict) {
            return;
        }
        List<Node> currentNodes = new LinkedList<>();
        if (node instanceof ComplexNodes) {
            List<Node> nodes = ((ComplexNodes) node).getNodes();
            if (nodes != null) {
                currentNodes.addAll(nodes);
            }
        } else {
            currentNodes.add(node);
        }
        List<Key<?>> unDeclaredKeys = new LinkedList<>();
        for (Node currentNode : currentNodes) {
            if (currentNode instanceof KeyManager) {
                Set<Key<?>> readableKeys = ((KeyManager) currentNode).getReadableKeys();
                Set<Key<?>> writableKeys = ((KeyManager) currentNode).getWritableKeys();
                if (readableKeys != null) {
                    for (Key<?> readableKey : readableKeys) {
                        if (!this.declaringKeys.contains(readableKey)) {
                            unDeclaredKeys.add(readableKey);
                        }
                    }
                }
                if (writableKeys != null) {
                    for (Key<?> writableKey : writableKeys) {
                        if (!this.declaringKeys.contains(writableKey)) {
                            unDeclaredKeys.add(writableKey);
                        }
                    }
                }
            }
        }
        // TODO 如果有未注册key，抛异常
        if (!unDeclaredKeys.isEmpty()) {
            throw new UnregisteredKeyException(String.format("unregistered keys:%s was found in node:%s", unDeclaredKeys, node));
        }
    }

    /**
     * 增加执行节点
     *
     * @param node 可执行节点
     * @return 当前实例
     */
    public ProcessDefinitionBuilder<R> addProcessNode(@NotNull ExecutableNode<?, ?> node) {
        addNode(node);
        return this;
    }

    /**
     * 增加执行节点
     *
     * @param nodes 可执行节点
     * @return 当前实例
     */
    public ProcessDefinitionBuilder<R> addProcessNodes(@NotNull ExecutableNode<?, ?>... nodes) {
        addNode(nodes);
        return this;
    }

    /**
     * 增加执行节点
     *
     * @param nodes 可执行节点
     * @return 当前实例
     */
    public ProcessDefinitionBuilder<R> addProcessNodes(@NotNull List<ExecutableNode<?, ?>> nodes) {
        addNode(nodes.toArray(new ExecutableNode[0]));
        return this;
    }

    /**
     * 增加if节点
     *
     * @param node 条件节点
     * @return 当前实例
     */
    public ProcessDefinitionBuilder<R> addIf(@NotNull IfConditionNode node) {
        addNode(node);
        return this;
    }

    /**
     * 增加while节点
     *
     * @param node 条件节点
     * @return 当前实例
     */
    public ProcessDefinitionBuilder<R> addWhile(@NotNull WhileConditionNode node) {
        addNode(node);
        return this;
    }

    /**
     * 增加dowhile节点
     *
     * @param node 条件节点
     * @return 当前实例
     */
    public ProcessDefinitionBuilder<R> addDoWhile(@NotNull DoWhileConditionNode node) {
        addNode(node);
        return this;
    }

    /**
     * 增加节点组
     *
     * @param group 节点组
     * @return 当前实例
     */
    public ProcessDefinitionBuilder<R> addGroup(@NotNull NodeGroup group) {
        addNode(group);
        return this;
    }

    /**
     * 增加聚合节点
     *
     * @param node 聚合节点
     * @return 当前实例
     */
    public <I, O> ProcessDefinitionBuilder<R> addAggregateNode(@NotNull AggregatableNode<I, O> node) {
        addNode(node);
        return this;
    }

    /**
     * 增加聚合节点
     *
     * @param node 聚合节点
     * @return 当前实例
     */
    public <O> ProcessDefinitionBuilder<R> addDistributeAggregateNode(@NotNull DistributeAggregatableNode<O> node) {
        addNode(node);
        return this;
    }

    /**
     * 增加分支节点
     *
     * @param node 分支节点
     * @return
     */
    public ProcessDefinitionBuilder<R> addBranchNode(@NotNull BranchNode node) {
        addNode(node);
        return this;
    }

    public ProcessDefinitionBuilder<R> initializeMode(@NotNull InitializeMode mode) {
        this.initializeMode = mode;
        return this;
    }

    public ProcessDefinitionBuilder<R> resultKey(@NotNull Key<R> key) {
        this.resultKey = key;
        return this;
    }

    public ProcessDefinitionBuilder<R> returnOn(ReturnCondition<R> condition) {
        this.returnCondition = condition;
        return this;
    }

    /**
     * 注册包装处理器
     *
     * @param handlers 包装处理器
     * @return 当前流程定义
     */
    public ProcessDefinitionBuilder<R> wrap(@NotNull StatusWrapperHandler... handlers) {
        if (handlers.length == 0) {
            return this;
        }
        if (this.handlers == null) {
            this.handlers = new LinkedList<>();
        }
        this.handlers.addAll(Arrays.asList(handlers));
        return this;
    }

    public ProcessDefinitionBuilder<R> declaredRestrict(boolean restrict) {
        this.declaredRestrict = restrict;
        return this;
    }

    public ProcessDefinitionBuilder<R> declaringKeys(Key<?>... keys) {
        if (keys != null && keys.length > 0) {
            this.declaringKeys.addAll(Arrays.asList(keys));
        }
        return this;
    }

    public ProcessDefinitionBuilder<R> declaringKeys(List<Key<?>> keys) {
        if (keys != null) {
            this.declaringKeys.addAll(keys);
        }
        return this;
    }


    @Override
    public ProcessDefinition<R> build() {
        returnableCheck(nodes);
        checkKeyRegistry();
        return new DefaultProcessDefinition<>(initializeMode, nodes, resultKey, returnCondition, handlers,
            declaringKeys, declaredRestrict);
    }
}

package cn.ideabuffer.process.core;

import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.exceptions.IllegalResultKeyException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public class DefaultProcessDefinition<R> implements ProcessDefinition<R> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private InitializeMode initializeMode = InitializeMode.ON_REGISTER;

    private Node[] nodes = new Node[0];

    @Nullable
    private Key<R> resultKey;

    private ReturnCondition<R> returnCondition;

    private List<StatusWrapperHandler> handlers;

    private List<Key<?>> declaringKeys;

    public DefaultProcessDefinition() {
        this(null);
    }

    public DefaultProcessDefinition(@Nullable Key<R> resultKey) {
        this.resultKey = resultKey;
        this.declaringKeys = new LinkedList<>();
    }

    protected ProcessDefinition<R> addNode(@NotNull Node... nodes) {
        if (nodes.length == 0) {
            return this;
        }
        returnableCheck(nodes);
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
        return this;
    }

    private void returnableCheck(Node... nodes) {
        for (Node node : nodes) {
            if (node instanceof ExecutableNode) {
                Key<?> nodeResultKey = ((ExecutableNode)node).getResultKey();
                if (((ExecutableNode)node).getReturnCondition() != null) {
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
                    && ((ExecutableNode)node).getReturnCondition() == null) {
                    //noinspection unchecked
                    ((ExecutableNode)node).returnOn(returnCondition);
                }
            }
        }
    }

    @Override
    public ProcessDefinition<R> addProcessNode(@NotNull ExecutableNode<?, ?> node) {
        return addNode(node);
    }

    @Override
    public ProcessDefinition<R> addProcessNodes(@NotNull List<ExecutableNode<?, ?>> nodes) {
        return addNode(nodes.toArray(new ExecutableNode[0]));
    }

    @Override
    public ProcessDefinition<R> addProcessNodes(@NotNull ExecutableNode<?, ?>... nodes) {
        return addNode(nodes);
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
    public <I, O> ProcessDefinition<R> addAggregateNode(@NotNull AggregatableNode<I, O> node) {
        return addNode(node);
    }

    @Override
    public <O> ProcessDefinition<R> addDistributeAggregateNode(@NotNull DistributeAggregatableNode<O> node) {
        return addNode(node);
    }

    @Override
    public ProcessDefinition<R> addBranchNode(@NotNull BranchNode node) {
        return addNode(node);
    }

    @NotNull
    @Override
    public Node[] getNodes() {
        return nodes;
    }

    public void setNodes(@NotNull Node[] nodes) {
        addNode(nodes);
    }

    @Override
    public InitializeMode getInitializeMode() {
        return initializeMode;
    }

    @Override
    public ProcessDefinition<R> initializeMode(@NotNull InitializeMode mode) {
        this.initializeMode = mode;
        return this;
    }

    @NotNull
    @Override
    public ProcessInstance<R> newInstance() {
        return new DefaultProcessInstance<>(this);
    }

    @Override
    public ProcessDefinition<R> resultKey(@NotNull Key<R> key) {
        this.resultKey = key;
        return this;
    }

    @Nullable
    @Override
    public Key<R> getResultKey() {
        return this.resultKey;
    }

    @Override
    public void returnOn(ReturnCondition<R> condition) {
        this.returnCondition = condition;
    }

    @Override
    public ReturnCondition<R> getReturnCondition() {
        return this.returnCondition;
    }

    @Override
    public ProcessDefinition<R> wrap(@NotNull StatusWrapperHandler... handlers) {
        if (handlers.length == 0) {
            return this;
        }
        if (this.handlers == null) {
            this.handlers = new LinkedList<>();
        }
        this.handlers.addAll(Arrays.asList(handlers));
        return this;
    }

    @NotNull
    @Override
    public List<StatusWrapperHandler> getHandlers() {
        if (this.handlers == null) {
            this.handlers = Collections.emptyList();
        }
        return Collections.unmodifiableList(this.handlers);
    }

    @Override
    public ProcessDefinition<R> declaringKeys(Key<?>... keys) {
        if (keys != null && keys.length > 0) {
            this.declaringKeys.addAll(Arrays.asList(keys));
        }
        return this;
    }

    @Override
    public ProcessDefinition<R> declaringKeys(List<Key<?>> keys) {
        if (keys != null) {
            this.declaringKeys.addAll(keys);
        }
        return this;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void destroy() {
        if (nodes != null) {
            LifecycleManager.destroy(Arrays.asList(nodes));
        }
    }
}
package cn.ideabuffer.process.core;

import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.exception.IllegalResultKeyException;
import cn.ideabuffer.process.core.exception.LifecycleException;
import cn.ideabuffer.process.core.nodes.*;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.nodes.condition.DoWhileConditionNode;
import cn.ideabuffer.process.core.nodes.condition.IfConditionNode;
import cn.ideabuffer.process.core.nodes.condition.WhileConditionNode;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public class DefaultProcessDefinition<R> implements ProcessDefinition<R> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private volatile LifecycleState state = LifecycleState.NEW;

    private InitializeMode initializeMode = InitializeMode.ON_REGISTER;

    private Node[] nodes = new Node[0];

    private Key<R> resultKey;

    private ReturnCondition<R> returnCondition;

    public DefaultProcessDefinition() {
        if (initializeMode == InitializeMode.ON_REGISTER) {
            state = LifecycleState.INITIALIZED;
        }
    }

    protected ProcessDefinition<R> addNode(@NotNull Node... nodes) {
        if (nodes.length == 0) {
            return this;
        }
        returnableCheck(nodes);
        if (initializeMode == InitializeMode.ON_REGISTER) {
            try {
                Arrays.stream(nodes).forEach(Lifecycle::initialize);
            } catch (Exception e) {
                throw new LifecycleException("initialize failed on register", e);
            }
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
            if (node instanceof ExecutableNode && ((ExecutableNode)node).isReturnable()) {
                Key<?> nodeResultKey = ((ExecutableNode)node).getResultKey();
                // 没有设置结果key
                if (nodeResultKey == null) {
                    throw new NullPointerException("");
                }
                // 结果Key不一致
                if (!nodeResultKey.equals(resultKey)) {
                    throw new IllegalResultKeyException(String.format("resultKey[%s] of returnable node is not equals resultKey:[%s] of definition", nodeResultKey.toString(), resultKey.toString()));
                }
                // 没有返回条件，使用默认的返回条件
                if (((ExecutableNode)node).getReturnCondition() == null) {
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
    public void initialize() {
        if (initializeMode == InitializeMode.ON_REGISTER) {
            return;
        }

        if (state != LifecycleState.NEW) {
            return;
        }

        synchronized (this) {
            if (state != LifecycleState.NEW) {
                return;
            }
            try {
                state = LifecycleState.INITIALIZING;
                Arrays.stream(nodes).forEach(Lifecycle::initialize);
                state = LifecycleState.INITIALIZED;
            } catch (Exception e) {
                logger.error("initialize failed", e);
                throw e;
            }
        }
    }

    @Override
    public void destroy() {
        if (state != LifecycleState.INITIALIZED) {
            return;
        }
        synchronized (this) {
            if (state != LifecycleState.INITIALIZED) {
                return;
            }
            try {
                state = LifecycleState.DESTROYING;
                Arrays.stream(nodes).forEach(Lifecycle::destroy);
                state = LifecycleState.DESTROYED;
            } catch (Exception e) {
                logger.error("destroy failed", e);
                throw e;
            }
        }

    }

    @Override
    public @NotNull LifecycleState getState() {
        return state;
    }

    @Override
    public InitializeMode getInitializeMode() {
        return initializeMode;
    }

    @Override
    public ProcessInstance<R> newInstance() {
        return new DefaultProcessInstance<>(this);
    }

    @Override
    public ProcessDefinition<R> resultKey(Key<R> key) {
        this.resultKey = key;
        return this;
    }

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
}
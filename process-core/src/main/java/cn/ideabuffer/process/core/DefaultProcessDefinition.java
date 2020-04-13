package cn.ideabuffer.process.core;

import cn.ideabuffer.process.core.exception.LifecycleException;
import cn.ideabuffer.process.core.nodes.AggregatableNode;
import cn.ideabuffer.process.core.nodes.BaseNode;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.nodes.NodeGroup;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.nodes.condition.DoWhileConditionNode;
import cn.ideabuffer.process.core.nodes.condition.IfConditionNode;
import cn.ideabuffer.process.core.nodes.condition.WhileConditionNode;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public class DefaultProcessDefinition<R> implements ProcessDefinition<R> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private transient volatile LifecycleState state = LifecycleState.NEW;

    private transient InitializeMode initializeMode = InitializeMode.ON_REGISTER;

    private Node[] nodes = new Node[0];

    private BaseNode<R> baseNode;

    public DefaultProcessDefinition() {
        if (initializeMode == InitializeMode.ON_REGISTER) {
            state = LifecycleState.INITIALIZED;
        }
    }

    private ProcessDefinition<R> addNode(@NotNull Node... nodes) {
        if (nodes.length == 0) {
            return this;
        }
        if (initializeMode == InitializeMode.ON_REGISTER) {
            try {
                Arrays.stream(nodes).forEach(Lifecycle::initialize);
            } catch (Throwable t) {
                throw new LifecycleException("initialize failed on register", t);
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

    @Override
    public ProcessDefinition<R> addProcessNodes(@NotNull ExecutableNode... nodes) {
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

    @NotNull
    @Override
    public Node[] getNodes() {
        return nodes;
    }

    public void setNodes(@NotNull Node[] nodes) {
        addNode(nodes);
    }

    @Override
    public BaseNode<R> getBaseNode() {
        return baseNode;
    }

    public void setBaseNode(BaseNode<R> baseNode) {
        this.baseNode = baseNode;
        if (initializeMode == InitializeMode.ON_REGISTER) {
            try {
                baseNode.initialize();
            } catch (Throwable t) {
                throw new LifecycleException("initialize failed on register", t);
            }
        }
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
            } catch (Throwable t) {
                logger.error("initialize failed", t);
                throw t;
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
            } catch (Throwable t) {
                logger.error("destroy failed", t);
                throw t;
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
}
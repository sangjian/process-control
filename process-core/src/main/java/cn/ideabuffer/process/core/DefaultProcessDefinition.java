package cn.ideabuffer.process.core;

import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.processors.wrapper.StatusWrapperHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

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

    private Set<Key<?>> declaringKeys;

    private boolean declaredRestrict;

    private String name;

    private String description;

    DefaultProcessDefinition(InitializeMode initializeMode, Node[] nodes, @Nullable Key<R> resultKey,
                                    ReturnCondition<R> returnCondition, List<StatusWrapperHandler> handlers,
                                    Set<Key<?>> declaringKeys, boolean declaredRestrict, String name, String description) {
        this.initializeMode = initializeMode;
        this.nodes = nodes;
        this.resultKey = resultKey;
        this.returnCondition = returnCondition;
        this.handlers = handlers;
        this.declaringKeys = declaringKeys == null ? new HashSet<>() : declaringKeys;
        this.declaredRestrict = declaredRestrict;
        this.name = name;
        this.description = description;
    }

    @NotNull
    @Override
    public Node[] getNodes() {
        return nodes;
    }

    @Override
    public InitializeMode getInitializeMode() {
        return initializeMode;
    }

    @NotNull
    @Override
    public ProcessInstance<R> newInstance() {
        return new DefaultProcessInstance<>(this);
    }

    @Nullable
    @Override
    public Key<R> getResultKey() {
        return this.resultKey;
    }

    @Override
    public ReturnCondition<R> getReturnCondition() {
        return this.returnCondition;
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
    public boolean isDeclaredRestrict() {
        return this.declaredRestrict;
    }

    @Override
    public Set<Key<?>> getDeclaredKeys() {
        return Collections.unmodifiableSet(this.declaringKeys);
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

    @Override
    public String getName() {
        return name != null ? name : this.getClass().getSimpleName();
    }

    @Override
    public String getDescription() {
        return description;
    }
}
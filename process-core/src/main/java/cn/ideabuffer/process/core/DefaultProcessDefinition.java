package cn.ideabuffer.process.core;

import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.processors.wrapper.StatusWrapperHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public class DefaultProcessDefinition<R> implements ProcessDefinition<R> {

    private InitializeMode initializeMode = InitializeMode.ON_REGISTER;

    private Node[] nodes = new Node[0];

    @Nullable
    private ResultHandler<R> resultHandler;

    private List<StatusWrapperHandler> handlers;

    private Set<Key<?>> declaringKeys;

    private String name;

    private String description;

    DefaultProcessDefinition(InitializeMode initializeMode, Node[] nodes, @Nullable ResultHandler<R> resultHandler,
                             List<StatusWrapperHandler> handlers,
                             Set<Key<?>> declaringKeys, String name, String description) {
        if (initializeMode != null) {
            this.initializeMode = initializeMode;
        }
        if (nodes != null) {
            this.nodes = nodes;
        }
        this.resultHandler = resultHandler;
        this.handlers = handlers;
        this.declaringKeys = declaringKeys == null ? new HashSet<>() : declaringKeys;
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
    public ResultHandler<R> getResultHandler() {
        return this.resultHandler;
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
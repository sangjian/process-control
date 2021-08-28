package cn.ideabuffer.process.core.nodes.builder;

import cn.ideabuffer.process.core.ProcessListener;
import cn.ideabuffer.process.core.ResultHandler;
import cn.ideabuffer.process.core.ReturnCondition;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.context.KeyMapper;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.nodes.NodeGroup;
import cn.ideabuffer.process.core.processors.NodeGroupProcessor;
import cn.ideabuffer.process.core.processors.impl.NodeGroupProcessorImpl;
import cn.ideabuffer.process.core.processors.wrapper.WrapperHandler;
import cn.ideabuffer.process.core.processors.wrapper.proxy.NodeGroupProcessorProxy;
import cn.ideabuffer.process.core.rules.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;

public class NodeGroupBuilder<R> extends AbstractExecutableNodeBuilder<R, NodeGroupProcessor<R>, NodeGroup<R>, WrapperHandler<R>> {

    private ExecutableNode<?, ?>[] nodes;
    private ResultHandler<R> resultHandler;
    
    private NodeGroupBuilder() {
        super(new NodeGroup<>());
    }
    
    public static <R> NodeGroupBuilder<R> newBuilder() {
        return new NodeGroupBuilder<>();
    }

    public NodeGroupBuilder<R> addNodes(@NotNull ExecutableNode<?, ?>... nodes) {
        this.nodes = nodes;
        return this;
    }

    public NodeGroupBuilder<R> resultHandler(@NotNull ResultHandler<R> resultHandler) {
        this.resultHandler = resultHandler;
        return this;
    }

    @Override
    public NodeGroupBuilder<R> parallel() {
        super.parallel();
        return this;
    }

    @Override
    public NodeGroupBuilder<R> parallel(Executor executor) {
        super.parallel(executor);
        return this;
    }

    @Override
    public NodeGroupBuilder<R> processOn(Rule rule) {
        super.processOn(rule);
        return this;
    }

    @Override
    public NodeGroupBuilder<R> addListeners(ProcessListener<R>... listeners) {
        super.addListeners(listeners);
        return this;
    }

    @Override
    public NodeGroupBuilder<R> by(NodeGroupProcessor<R> processor) {
        super.by(processor);
        return this;
    }

    @Override
    public NodeGroupBuilder<R> resultKey(Key<R> resultKey) {
        super.resultKey(resultKey);
        return this;
    }

    @Override
    public NodeGroupBuilder<R> returnOn(ReturnCondition<R> condition) {
        super.returnOn(condition);
        return this;
    }

    @Override
    public NodeGroupBuilder<R> keyMapper(KeyMapper keyMapper) {
        super.keyMapper(keyMapper);
        return this;
    }

    @Override
    public NodeGroupBuilder<R> readableKeys(@NotNull Key<?>... keys) {
        super.readableKeys(keys);
        return this;
    }

    @Override
    public NodeGroupBuilder<R> readableKeys(@NotNull Set<Key<?>> keys) {
        super.readableKeys(keys);
        return this;
    }

    @Override
    public NodeGroupBuilder<R> writableKeys(@NotNull Key<?>... keys) {
        super.writableKeys(keys);
        return this;
    }

    @Override
    public NodeGroupBuilder<R> writableKeys(@NotNull Set<Key<?>> keys) {
        super.writableKeys(keys);
        return this;
    }

    @Override
    public NodeGroupBuilder<R> enabled(BooleanSupplier supplier) {
        super.enabled(supplier);
        return this;
    }

    @Override
    public NodeGroupBuilder<R> wrap(@NotNull WrapperHandler<R> handler) {
        super.wrap(handler);
        return this;
    }

    @Override
    public NodeGroupBuilder<R> wrap(@NotNull List<WrapperHandler<R>> handlers) {
        super.wrap(handlers);
        return this;
    }

    @Override
    public NodeGroupBuilder<R> name(String name) {
        super.name(name);
        return this;
    }

    @Override
    public NodeGroupBuilder<R> description(String description) {
        super.description(description);
        return this;
    }

    @Override
    public NodeGroup<R> build() {
        if (processor == null) {
            processor = new NodeGroupProcessorImpl<>(Arrays.asList(nodes), resultHandler);
        }
        processor = NodeGroupProcessorProxy.wrap(processor, handlers);
        if (readableKeys == null) {
            readableKeys = new HashSet<>();
        }
        if (nodes != null && nodes.length > 0) {
            readableKeys.addAll(Arrays.stream(nodes).map(ExecutableNode::getResultKey).collect(Collectors.toList()));
        }
        return super.build();
    }
}

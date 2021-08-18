package cn.ideabuffer.process.core.nodes.builder;

import cn.ideabuffer.process.core.Builder;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.context.KeyMapper;
import cn.ideabuffer.process.core.nodes.DefaultDistributeMergeableNode;
import cn.ideabuffer.process.core.nodes.DistributeMergeableNode;
import cn.ideabuffer.process.core.processors.DistributeProcessor;
import cn.ideabuffer.process.core.processors.wrapper.WrapperHandler;
import cn.ideabuffer.process.core.processors.wrapper.proxy.DistributeProcessorProxy;
import cn.ideabuffer.process.core.rules.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author sangjian.sj
 * @date 2020/04/24
 */
public class DistributeMergeableNodeBuilder<T, R> implements Builder<DistributeMergeableNode<T, R>> {

    private Rule rule;
    private long timeout;
    private TimeUnit timeUnit;
    private DistributeProcessor<T, R> processor;
    private List<WrapperHandler<T>> handlers;
    private KeyMapper keyMapper;
    private Set<Key<?>> readableKeys;
    private Set<Key<?>> writableKeys;
    private String name;
    private String description;

    private DistributeMergeableNodeBuilder() {
        this.readableKeys = new HashSet<>();
        this.writableKeys = new HashSet<>();
    }

    public static <T, R> DistributeMergeableNodeBuilder<T, R> newBuilder() {
        return new DistributeMergeableNodeBuilder<>();
    }

    public DistributeMergeableNodeBuilder<T, R> processOn(Rule rule) {
        this.rule = rule;
        return this;
    }

    public DistributeMergeableNodeBuilder<T, R> timeout(long timeout, @NotNull TimeUnit unit) {
        this.timeout = timeout;
        this.timeUnit = unit;
        return this;
    }

    public DistributeMergeableNodeBuilder<T, R> by(DistributeProcessor<T, R> processor) {
        this.processor = processor;
        return this;
    }

    public DistributeMergeableNodeBuilder<T, R> wrap(@NotNull WrapperHandler<T>... handlers) {
        return wrap(Arrays.asList(handlers));
    }

    public DistributeMergeableNodeBuilder<T, R> wrap(@NotNull List<WrapperHandler<T>> handlers) {
        if (handlers.isEmpty()) {
            return this;
        }
        if (this.handlers == null) {
            this.handlers = new LinkedList<>();
        }
        this.handlers.addAll(handlers);
        return this;
    }

    public DistributeMergeableNodeBuilder<T, R> keyMapper(KeyMapper keyMapper) {
        this.keyMapper = keyMapper;
        return this;
    }

    public DistributeMergeableNodeBuilder<T, R> readableKeys(@NotNull Key<?>... keys) {
        this.readableKeys.addAll(Arrays.stream(keys).collect(Collectors.toSet()));
        return this;
    }

    public DistributeMergeableNodeBuilder<T, R> readableKeys(@NotNull Set<Key<?>> keys) {
        this.readableKeys = keys;
        return this;
    }

    public DistributeMergeableNodeBuilder<T, R> writableKeys(@NotNull Key<?>... keys) {
        this.writableKeys.addAll(Arrays.stream(keys).collect(Collectors.toSet()));
        return this;
    }

    public DistributeMergeableNodeBuilder<T, R> writableKeys(@NotNull Set<Key<?>> keys) {
        this.writableKeys = keys;
        return this;
    }

    public DistributeMergeableNodeBuilder<T, R> name(String name) {
        this.name = name;
        return this;
    }

    public DistributeMergeableNodeBuilder<T, R> description(String description) {
        this.description = description;
        return this;
    }

    @Override
    public DistributeMergeableNode<T, R> build() {
        long millis = timeUnit == null ? 0L : timeUnit.toMillis(timeout);
        processor = DistributeProcessorProxy.wrap(processor, handlers);
        DistributeMergeableNode<T, R> node = new DefaultDistributeMergeableNode<>(rule, millis, processor, readableKeys, writableKeys, keyMapper);
        node.setName(name);
        node.setDescription(description);
        return node;
    }
}

package cn.ideabuffer.process.core.nodes.builder;

import cn.ideabuffer.process.core.Builder;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.context.KeyMapper;
import cn.ideabuffer.process.core.nodes.DefaultDistributeMergeableNode;
import cn.ideabuffer.process.core.nodes.DistributeMergeableNode;
import cn.ideabuffer.process.core.processors.DistributeMerger;
import cn.ideabuffer.process.core.processors.DistributeProcessor;
import cn.ideabuffer.process.core.processors.impl.DistributeProcessorAdaptor;
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
    private DistributeProcessor<T, R> distributeProcessor;
    private List<WrapperHandler<T>> handlers;
    private KeyMapper keyMapper;
    private Set<Key<?>> readableKeys;
    private Set<Key<?>> writableKeys;
    private String name;
    private String description;
    private String id;
    private Processor<T> processor;
    private DistributeMerger<T, R> merger;

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

    public DistributeMergeableNodeBuilder<T, R> by(@NotNull DistributeProcessor<T, R> distributeProcessor) {
        this.distributeProcessor = distributeProcessor;
        return this;
    }

    public DistributeMergeableNodeBuilder<T, R> by(@NotNull Processor<T> processor, @NotNull DistributeMerger<T, R> merger) {
        this.processor = processor;
        this.merger = merger;
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

    public DistributeMergeableNodeBuilder<T, R> id(String id) {
        this.id = id;
        return this;
    }

    @Override
    public DistributeMergeableNode<T, R> build() {
        long millis = timeUnit == null ? 0L : timeUnit.toMillis(timeout);
        if (distributeProcessor == null) {
            if (processor == null || merger == null) {
                throw new NullPointerException("processor and merger must be set when distributeProcessor is not set");
            }
            distributeProcessor = new DistributeProcessorAdaptor<>(processor, merger);
        }
        distributeProcessor = DistributeProcessorProxy.wrap(distributeProcessor, handlers);
        DistributeMergeableNode<T, R> node = new DefaultDistributeMergeableNode<>(rule, millis, distributeProcessor, readableKeys, writableKeys, keyMapper);
        node.setName(name);
        node.setDescription(description);
        node.setId(id);
        return node;
    }
}

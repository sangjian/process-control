package cn.ideabuffer.process.core.nodes.builder;

import cn.ideabuffer.process.core.Builder;
import cn.ideabuffer.process.core.ProcessListener;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.ReturnCondition;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.context.KeyMapper;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.processors.wrapper.WrapperHandler;
import cn.ideabuffer.process.core.rules.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;

import static cn.ideabuffer.process.core.Degradable.STRONG_DEPENDENCY;
import static cn.ideabuffer.process.core.Degradable.WEAK_DEPENDENCY;

/**
 * @author sangjian.sj
 * @date 2020/04/24
 */
public abstract class AbstractExecutableNodeBuilder<R, P extends Processor<R>, T extends ExecutableNode<R, P>, W
    extends WrapperHandler<R>>
    implements Builder<T> {

    protected boolean parallel;
    protected Executor executor;
    protected Rule rule;
    protected List<ProcessListener<R>> listeners;
    protected T node;
    protected P processor;
    protected Key<R> resultKey;
    protected ReturnCondition<R> returnCondition;
    protected KeyMapper keyMapper;
    protected Set<Key<?>> readableKeys;
    protected Set<Key<?>> writableKeys;
    protected BooleanSupplier enableSupplier;
    protected List<W> handlers;
    protected String id;
    protected String name;
    protected String description;
    protected BooleanSupplier weakDependencySupplier = STRONG_DEPENDENCY;
    protected Processor<R> fallbackProcessor;

    protected AbstractExecutableNodeBuilder(T node) {
        this.node = node;
        this.listeners = new LinkedList<>();
        this.readableKeys = new HashSet<>();
        this.writableKeys = new HashSet<>();
        this.handlers = new LinkedList<>();
    }

    public Builder<T> parallel() {
        this.parallel = true;
        return this;
    }

    public Builder<T> parallel(Executor executor) {
        this.executor = executor;
        return this;
    }

    public Builder<T> processOn(Rule rule) {
        this.rule = rule;
        return this;
    }

    @SuppressWarnings("unchecked")
    public Builder<T> addListeners(ProcessListener<R>... listeners) {
        if (listeners != null) {
            this.listeners.addAll(Arrays.stream(listeners).collect(Collectors.toList()));
        }
        return this;
    }

    public Builder<T> by(P processor) {
        this.processor = processor;
        return this;
    }

    public Builder<T> resultKey(Key<R> resultKey) {
        this.resultKey = resultKey;
        this.writableKeys.add(resultKey);
        return this;
    }

    protected Builder<T> returnOn(ReturnCondition<R> condition) {
        this.returnCondition = condition;
        return this;
    }

    protected Builder<T> keyMapper(KeyMapper keyMapper) {
        this.keyMapper = keyMapper;
        return this;
    }

    protected Builder<T> readableKeys(@NotNull Key<?>... keys) {
        this.readableKeys.addAll(Arrays.stream(keys).collect(Collectors.toSet()));
        return this;
    }

    protected Builder<T> readableKeys(@NotNull Set<Key<?>> keys) {
        this.readableKeys = keys;
        return this;
    }

    protected Builder<T> writableKeys(@NotNull Key<?>... keys) {
        this.writableKeys.addAll(Arrays.stream(keys).collect(Collectors.toSet()));
        return this;
    }

    protected Builder<T> writableKeys(@NotNull Set<Key<?>> keys) {
        this.writableKeys = keys;
        return this;
    }

    public Builder<T> enabled(BooleanSupplier supplier) {
        this.enableSupplier = supplier;
        return this;
    }

    public Builder<T> wrap(@NotNull W handler) {
        this.handlers.add(handler);
        return this;
    }

    public Builder<T> wrap(@NotNull List<W> handlers) {
        if (!handlers.isEmpty()) {
            this.handlers.addAll(handlers);
        }
        return this;
    }

    public Builder<T> id(String id) {
        this.id = id;
        return this;
    }

    public Builder<T> name(String name) {
        this.name = name;
        return this;
    }

    public Builder<T> description(String description) {
        this.description = description;
        return this;
    }

    public Builder<T> strongDependency() {
        this.weakDependencySupplier = STRONG_DEPENDENCY;
        return this;
    }

    public Builder<T> weakDependency() {
        this.weakDependencySupplier = WEAK_DEPENDENCY;
        return this;
    }

    public Builder<T> weakDependency(@NotNull BooleanSupplier supplier) {
        this.weakDependencySupplier = supplier;
        return this;
    }

    public Builder<T> fallbackBy(Processor<R> fallbackProcessor) {
        this.fallbackProcessor = fallbackProcessor;
        return this;
    }

    @Override
    public T build() {
        if (parallel) {
            node.parallel();
        }
        if (executor != null) {
            node.parallel(executor);
        }
        node.processOn(rule);
        if (listeners != null) {
            //noinspection unchecked
            node.addProcessListeners(listeners.toArray(new ProcessListener[0]));
        }
        if (processor == null) {
            throw new NullPointerException("processor cannot be null");
        }
        node.registerProcessor(processor);
        node.setResultKey(resultKey);
        node.returnOn(returnCondition);
        node.setKeyMapper(keyMapper);
        node.setReadableKeys(readableKeys);
        node.setWritableKeys(writableKeys);
        if (enableSupplier != null) {
            node.setEnabled(enableSupplier);
        }
        if (name != null) {
            node.setName(name);
        }
        node.setDescription(description);
        node.setWeakDependency(weakDependencySupplier);
        node.setFallbackProcessor(fallbackProcessor);
        node.setId(id);
        return node;
    }

}

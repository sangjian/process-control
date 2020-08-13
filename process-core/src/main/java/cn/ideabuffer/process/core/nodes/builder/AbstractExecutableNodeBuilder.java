package cn.ideabuffer.process.core.nodes.builder;

import cn.ideabuffer.process.core.ProcessListener;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.ReturnCondition;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.rule.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/**
 * @author sangjian.sj
 * @date 2020/04/24
 */
public abstract class AbstractExecutableNodeBuilder<R, P extends Processor<R>, T extends ExecutableNode<R, P>>
    implements Builder<T> {

    protected boolean parallel;

    protected Executor executor;

    protected Rule rule;

    protected List<ProcessListener<R>> listeners;

    protected T node;

    protected P processor;

    protected Key<R> resultKey;

    protected ReturnCondition<R> returnCondition;

    protected Set<Key<?>> requiredKeys;

    protected AbstractExecutableNodeBuilder(T node) {
        this.node = node;
        this.listeners = new LinkedList<>();
        this.requiredKeys = new HashSet<>();
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
        this.requiredKeys.add(resultKey);
        return this;
    }

    public Builder<T> returnOn(ReturnCondition<R> condition) {
        this.returnCondition = condition;
        return this;
    }

    public Builder<T> require(@NotNull Key<?> key) {
        this.requiredKeys.add(key);
        return this;
    }

    public Builder<T> require(@NotNull Key<?>... keys) {
        this.requiredKeys.addAll(Arrays.stream(keys).collect(Collectors.toSet()));
        return this;
    }

    public Builder<T> require(@NotNull Set<Key<?>> keys) {
        this.requiredKeys = keys;
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
        node.registerProcessor(processor);
        node.setResultKey(resultKey);
        node.returnOn(returnCondition);
        node.setRequiredKeys(requiredKeys);
        return node;
    }

}

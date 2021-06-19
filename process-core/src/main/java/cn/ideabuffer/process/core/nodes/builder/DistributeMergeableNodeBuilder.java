package cn.ideabuffer.process.core.nodes.builder;

import cn.ideabuffer.process.core.nodes.DefaultDistributeMergeableNode;
import cn.ideabuffer.process.core.nodes.DistributeMergeableNode;
import cn.ideabuffer.process.core.processors.DistributeProcessor;
import cn.ideabuffer.process.core.processors.wrapper.WrapperHandler;
import cn.ideabuffer.process.core.processors.wrapper.proxy.DistributeProcessorProxy;
import cn.ideabuffer.process.core.rule.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    private DistributeMergeableNodeBuilder() {
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

    @Override
    public DistributeMergeableNode<T, R> build() {
        long millis = timeUnit == null ? 0L : timeUnit.toMillis(timeout);
        processor = DistributeProcessorProxy.wrap(processor, handlers);
        return new DefaultDistributeMergeableNode<>(rule, millis, processor);
    }
}

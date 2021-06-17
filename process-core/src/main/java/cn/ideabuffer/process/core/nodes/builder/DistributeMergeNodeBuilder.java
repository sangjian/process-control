package cn.ideabuffer.process.core.nodes.builder;

import cn.ideabuffer.process.core.nodes.DistributeMergeNode;
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
public class DistributeMergeNodeBuilder<T, R> implements Builder<DistributeMergeNode<T, R>> {

    private Rule rule;
    private long timeout;
    private TimeUnit timeUnit;
    private DistributeProcessor<T, R> processor;
    private List<WrapperHandler<T>> handlers;

    private DistributeMergeNodeBuilder() {
    }

    public static <T, R> DistributeMergeNodeBuilder<T, R> newBuilder() {
        return new DistributeMergeNodeBuilder<>();
    }

    public DistributeMergeNodeBuilder<T, R> processOn(Rule rule) {
        this.rule = rule;
        return this;
    }

    public DistributeMergeNodeBuilder<T, R> timeout(long timeout, @NotNull TimeUnit unit) {
        this.timeout = timeout;
        this.timeUnit = unit;
        return this;
    }

    public DistributeMergeNodeBuilder<T, R> by(DistributeProcessor<T, R> processor) {
        this.processor = processor;
        return this;
    }

    public DistributeMergeNodeBuilder<T, R> wrap(@NotNull WrapperHandler<T>... handlers) {
        return wrap(Arrays.asList(handlers));
    }

    public DistributeMergeNodeBuilder<T, R> wrap(@NotNull List<WrapperHandler<T>> handlers) {
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
    public DistributeMergeNode<T, R> build() {
        long millis = timeUnit == null ? 0L : timeUnit.toMillis(timeout);
        processor = DistributeProcessorProxy.wrap(processor, handlers);
        return new DistributeMergeNode<>(rule, millis, processor);
    }
}

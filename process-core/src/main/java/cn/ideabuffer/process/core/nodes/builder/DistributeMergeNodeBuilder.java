package cn.ideabuffer.process.core.nodes.builder;

import cn.ideabuffer.process.core.handler.ExceptionHandler;
import cn.ideabuffer.process.core.nodes.DistributeMergeNode;
import cn.ideabuffer.process.core.processors.DistributeProcessor;
import cn.ideabuffer.process.core.rule.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

/**
 * @author sangjian.sj
 * @date 2020/04/24
 */
public class DistributeMergeNodeBuilder<T, R> implements Builder<DistributeMergeNode<T, R>> {

    private Rule rule;
    private ExceptionHandler exceptionHandler;
    private long timeout;
    private TimeUnit timeUnit;
    private DistributeProcessor<T, R> processor;

    private DistributeMergeNodeBuilder() {
    }

    public static <T, R> DistributeMergeNodeBuilder<T, R> newBuilder() {
        return new DistributeMergeNodeBuilder<>();
    }

    public DistributeMergeNodeBuilder<T, R> exceptionHandler(ExceptionHandler handler) {
        this.exceptionHandler = handler;
        return this;
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

    @Override
    public DistributeMergeNode<T, R> build() {
        long millis = timeUnit == null ? 0L : timeUnit.toMillis(timeout);
        return new DistributeMergeNode<>(rule, exceptionHandler, millis, processor);
    }
}

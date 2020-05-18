package cn.ideabuffer.process.core.nodes.builder;

import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.handler.ExceptionHandler;
import cn.ideabuffer.process.core.nodes.MergeNode;
import cn.ideabuffer.process.core.rule.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

/**
 * @author sangjian.sj
 * @date 2020/04/24
 */
public class MergeNodeBuilder<T> implements Builder<MergeNode<T>> {

    private Rule rule;
    private ExceptionHandler exceptionHandler;
    private long timeout;
    private TimeUnit timeUnit;
    private Processor<T> processor;

    private MergeNodeBuilder() {
    }

    public static <T> MergeNodeBuilder<T> newBuilder() {
        return new MergeNodeBuilder<>();
    }

    public MergeNodeBuilder<T> exceptionHandler(ExceptionHandler handler) {
        this.exceptionHandler = handler;
        return this;
    }

    public MergeNodeBuilder<T> processOn(Rule rule) {
        this.rule = rule;
        return this;
    }

    public MergeNodeBuilder<T> timeout(long timeout, @NotNull TimeUnit unit) {
        this.timeout = timeout;
        this.timeUnit = unit;
        return this;
    }

    public MergeNodeBuilder<T> by(Processor<T> processor) {
        this.processor = processor;
        return this;
    }

    @Override
    public MergeNode<T> build() {
        long millis = timeUnit == null ? 0L : timeUnit.toMillis(timeout);
        return new MergeNode<>(rule, exceptionHandler, millis, processor);
    }
}

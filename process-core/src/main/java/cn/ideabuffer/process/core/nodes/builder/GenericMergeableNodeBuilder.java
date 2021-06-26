package cn.ideabuffer.process.core.nodes.builder;

import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.nodes.DefaultGenericMergeableNode;
import cn.ideabuffer.process.core.nodes.GenericMergeableNode;
import cn.ideabuffer.process.core.rules.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

/**
 * @author sangjian.sj
 * @date 2020/04/24
 */
public class GenericMergeableNodeBuilder<T> implements Builder<GenericMergeableNode<T>> {

    private Rule rule;
    private long timeout;
    private TimeUnit timeUnit;
    private Processor<T> processor;

    private GenericMergeableNodeBuilder() {
    }

    public static <T> GenericMergeableNodeBuilder<T> newBuilder() {
        return new GenericMergeableNodeBuilder<>();
    }

    public GenericMergeableNodeBuilder<T> processOn(Rule rule) {
        this.rule = rule;
        return this;
    }

    public GenericMergeableNodeBuilder<T> timeout(long timeout, @NotNull TimeUnit unit) {
        this.timeout = timeout;
        this.timeUnit = unit;
        return this;
    }

    public GenericMergeableNodeBuilder<T> by(Processor<T> processor) {
        this.processor = processor;
        return this;
    }

    @Override
    public GenericMergeableNode<T> build() {
        long millis = timeUnit == null ? 0L : timeUnit.toMillis(timeout);
        return new DefaultGenericMergeableNode<>(rule, millis, processor);
    }
}

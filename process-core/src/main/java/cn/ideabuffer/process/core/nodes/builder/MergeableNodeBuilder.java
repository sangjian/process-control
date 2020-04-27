package cn.ideabuffer.process.core.nodes.builder;

import cn.ideabuffer.process.core.handler.ExceptionHandler;
import cn.ideabuffer.process.core.nodes.MergeableNode;
import cn.ideabuffer.process.core.rule.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

/**
 * @author sangjian.sj
 * @date 2020/04/24
 */
public class MergeableNodeBuilder<T> implements Builder<MergeableNode<T>> {

    private Rule rule;
    private ExceptionHandler exceptionHandler;
    private long timeout;
    private TimeUnit timeUnit;
    private MergeableNode<T> node;

    private MergeableNodeBuilder(MergeableNode<T> node) {
        this.node = node;
    }

    public static <T> MergeableNodeBuilder newBuilder(MergeableNode<T> node) {
        return new MergeableNodeBuilder<>(node);
    }

    public MergeableNodeBuilder<T> exceptionHandler(ExceptionHandler handler) {
        this.exceptionHandler = handler;
        return this;
    }

    public MergeableNodeBuilder<T> processOn(Rule rule) {
        this.rule = rule;
        return this;
    }

    public MergeableNodeBuilder<T> timeout(long timeout, @NotNull TimeUnit unit) {
        this.timeout = timeout;
        this.timeUnit = unit;
        return this;
    }

    @Override
    public MergeableNode<T> build() {
        node.exceptionHandler(exceptionHandler);
        node.processOn(rule);
        node.timeout(timeout, timeUnit);
        return node;
    }
}

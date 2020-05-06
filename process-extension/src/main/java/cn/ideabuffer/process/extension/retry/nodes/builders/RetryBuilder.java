package cn.ideabuffer.process.extension.retry.nodes.builders;

import cn.ideabuffer.process.core.ProcessListener;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.nodes.builder.AbstractExecutableNodeBuilder;
import cn.ideabuffer.process.core.rule.Rule;
import cn.ideabuffer.process.extension.retry.nodes.DefaultRetryNode;
import cn.ideabuffer.process.extension.retry.nodes.RetryableNode;
import com.github.rholder.retry.Retryer;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/05/06
 */
public class RetryBuilder<R> extends AbstractExecutableNodeBuilder<R, Processor<R>, RetryableNode<R>> {

    private Retryer<R> retryer;

    protected RetryBuilder(RetryableNode<R> node) {
        super(node);
    }

    public static <R> RetryBuilder<R> newBuilder() {
        return new RetryBuilder<>(new DefaultRetryNode<>());
    }

    @Override
    public RetryBuilder<R> parallel() {
        super.parallel();
        return this;
    }

    @Override
    public RetryBuilder<R> parallel(Executor executor) {
        super.parallel(executor);
        return this;
    }

    @Override
    public RetryBuilder<R> processOn(Rule rule) {
        super.processOn(rule);
        return this;
    }

    @Override
    public RetryBuilder<R> addListeners(ProcessListener... listeners) {
        super.addListeners(listeners);
        return this;
    }

    @Override
    public RetryBuilder<R> by(Processor<R> processor) {
        super.by(processor);
        return this;
    }

    public RetryBuilder<R> retryBy(@NotNull Retryer<R> retryer) {
        this.retryer = retryer;
        return this;
    }

    @Override
    public RetryableNode<R> build() {
        RetryableNode<R> node = super.build();
        node.retryBy(retryer);
        return node;
    }
}

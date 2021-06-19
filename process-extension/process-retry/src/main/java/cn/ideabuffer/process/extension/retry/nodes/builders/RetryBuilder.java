package cn.ideabuffer.process.extension.retry.nodes.builders;

import cn.ideabuffer.process.core.ProcessListener;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.context.KeyMapper;
import cn.ideabuffer.process.core.nodes.builder.AbstractExecutableNodeBuilder;
import cn.ideabuffer.process.core.processors.wrapper.WrapperHandler;
import cn.ideabuffer.process.core.processors.wrapper.proxy.DefaultProcessorProxy;
import cn.ideabuffer.process.core.rule.Rule;
import cn.ideabuffer.process.extension.retry.nodes.DefaultRetryNode;
import cn.ideabuffer.process.extension.retry.nodes.RetryableNode;
import com.github.rholder.retry.Retryer;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;

/**
 * @author sangjian.sj
 * @date 2020/05/06
 */
public class RetryBuilder<R> extends AbstractExecutableNodeBuilder<R, Processor<R>, RetryableNode<R>, WrapperHandler<R>> {

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
    public RetryBuilder<R> addListeners(ProcessListener<R>... listeners) {
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
    public RetryBuilder<R> resultKey(Key<R> resultKey) {
        super.resultKey(resultKey);
        return this;
    }

    @Override
    public RetryBuilder<R> keyMapper(KeyMapper keyMapper) {
        super.keyMapper(keyMapper);
        return this;
    }

    @Override
    public RetryBuilder<R> readableKeys(@NotNull Key<?>... keys) {
        super.readableKeys(keys);
        return this;
    }

    @Override
    public RetryBuilder<R> readableKeys(@NotNull Set<Key<?>> keys) {
        super.readableKeys(keys);
        return this;
    }

    @Override
    public RetryBuilder<R> writableKeys(@NotNull Key<?>... keys) {
        super.writableKeys(keys);
        return this;
    }

    @Override
    public RetryBuilder<R> writableKeys(@NotNull Set<Key<?>> keys) {
        super.writableKeys(keys);
        return this;
    }

    @Override
    public RetryBuilder<R> enabled(BooleanSupplier supplier) {
        super.enabled(supplier);
        return this;
    }

    @Override
    public RetryBuilder<R> wrap(@NotNull WrapperHandler<R> handler) {
        super.wrap(handler);
        return this;
    }

    @Override
    public RetryBuilder<R> wrap(@NotNull List<WrapperHandler<R>> handlers) {
        super.wrap(handlers);
        return this;
    }

    @Override
    public RetryableNode<R> build() {
        if (processor == null) {
            throw new NullPointerException("processor cannot be null");
        }
        processor = DefaultProcessorProxy.wrap(processor, handlers);
        RetryableNode<R> node = super.build();
        node.retryBy(retryer);
        return node;
    }
}

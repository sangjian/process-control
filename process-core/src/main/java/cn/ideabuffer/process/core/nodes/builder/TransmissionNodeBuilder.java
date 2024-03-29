package cn.ideabuffer.process.core.nodes.builder;

import cn.ideabuffer.process.core.Builder;
import cn.ideabuffer.process.core.ProcessListener;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.ReturnCondition;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.context.KeyMapper;
import cn.ideabuffer.process.core.nodes.TransmissionNode;
import cn.ideabuffer.process.core.processors.wrapper.WrapperHandler;
import cn.ideabuffer.process.core.processors.wrapper.proxy.DefaultProcessorProxy;
import cn.ideabuffer.process.core.rules.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;

/**
 * @author sangjian.sj
 * @date 2020/04/24
 */
public class TransmissionNodeBuilder<R>
    extends AbstractExecutableNodeBuilder<R, Processor<R>, TransmissionNode<R>, WrapperHandler<R>> {

    private TransmissionNodeBuilder(TransmissionNode<R> node) {
        super(node);
    }

    public static <R> TransmissionNodeBuilder<R> newBuilder() {
        return new TransmissionNodeBuilder<>(new TransmissionNode<>());
    }

    @Override
    public TransmissionNodeBuilder<R> parallel() {
        super.parallel();
        return this;
    }

    @Override
    public TransmissionNodeBuilder<R> parallel(Executor executor) {
        super.parallel(executor);
        return this;
    }

    @Override
    public TransmissionNodeBuilder<R> processOn(Rule rule) {
        super.processOn(rule);
        return this;
    }

    @Override
    public TransmissionNodeBuilder<R> addListeners(ProcessListener<R>... listeners) {
        super.addListeners(listeners);
        return this;
    }

    @Override
    public TransmissionNodeBuilder<R> by(Processor<R> processor) {
        super.by(processor);
        return this;
    }

    @Override
    public TransmissionNodeBuilder<R> resultKey(Key<R> resultKey) {
        super.resultKey(resultKey);
        return this;
    }

    @Override
    public TransmissionNodeBuilder<R> returnOn(ReturnCondition<R> condition) {
        super.returnOn(condition);
        return this;
    }

    @Override
    public TransmissionNodeBuilder<R> enabled(BooleanSupplier supplier) {
        super.enabled(supplier);
        return this;
    }

    @Override
    public TransmissionNodeBuilder<R> wrap(@NotNull WrapperHandler<R> handler) {
        super.wrap(handler);
        return this;
    }

    @Override
    public TransmissionNodeBuilder<R> wrap(@NotNull List<WrapperHandler<R>> handlers) {
        super.wrap(handlers);
        return this;
    }

    @Override
    public TransmissionNodeBuilder<R> name(String name) {
        super.name(name);
        return this;
    }

    @Override
    public TransmissionNodeBuilder<R> description(String description) {
        super.description(description);
        return this;
    }

    @Override
    public TransmissionNodeBuilder<R> keyMapper(KeyMapper keyMapper) {
        super.keyMapper(keyMapper);
        return this;
    }

    @Override
    public TransmissionNodeBuilder<R> readableKeys(@NotNull Key<?>... keys) {
        super.readableKeys(keys);
        return this;
    }

    @Override
    public TransmissionNodeBuilder<R> readableKeys(@NotNull Set<Key<?>> keys) {
        super.readableKeys(keys);
        return this;
    }

    @Override
    public TransmissionNodeBuilder<R> strongDependency() {
        super.strongDependency();
        return this;
    }

    @Override
    public TransmissionNodeBuilder<R> weakDependency() {
        super.weakDependency();
        return this;
    }

    @Override
    public TransmissionNodeBuilder<R> weakDependency(@NotNull BooleanSupplier supplier) {
        super.weakDependency(supplier);
        return this;
    }

    @Override
    public TransmissionNodeBuilder<R> fallbackBy(Processor<R> fallbackProcessor) {
        super.fallbackBy(fallbackProcessor);
        return this;
    }

    @Override
    public TransmissionNodeBuilder<R> id(String id) {
        super.id(id);
        return this;
    }

    @Override
    public TransmissionNode<R> build() {
        if (processor != null) {
            processor = DefaultProcessorProxy.wrap(processor, handlers);
        }
        return super.build();
    }
}

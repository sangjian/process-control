package cn.ideabuffer.process.core.nodes.builder;

import cn.ideabuffer.process.core.Builder;
import cn.ideabuffer.process.core.ProcessListener;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.ReturnCondition;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.context.KeyMapper;
import cn.ideabuffer.process.core.nodes.ProcessNode;
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
public class ProcessNodeBuilder<R>
    extends AbstractExecutableNodeBuilder<R, Processor<R>, ProcessNode<R>, WrapperHandler<R>> {

    private ProcessNodeBuilder() {
        super(new ProcessNode<>());
    }

    public static <R> ProcessNodeBuilder<R> newBuilder() {
        return new ProcessNodeBuilder<>();
    }

    @Override
    public ProcessNodeBuilder<R> parallel() {
        super.parallel();
        return this;
    }

    @Override
    public ProcessNodeBuilder<R> parallel(Executor executor) {
        super.parallel(executor);
        return this;
    }

    @Override
    public ProcessNodeBuilder<R> processOn(Rule rule) {
        super.processOn(rule);
        return this;
    }

    @Override
    public ProcessNodeBuilder<R> addListeners(ProcessListener<R>... listeners) {
        super.addListeners(listeners);
        return this;
    }

    @Override
    public ProcessNodeBuilder<R> by(Processor<R> processor) {
        super.by(processor);
        return this;
    }

    @Override
    public ProcessNodeBuilder<R> resultKey(Key<R> resultKey) {
        super.resultKey(resultKey);
        return this;
    }

    @Override
    public ProcessNodeBuilder<R> returnOn(ReturnCondition<R> condition) {
        super.returnOn(condition);
        return this;
    }

    @Override
    public ProcessNodeBuilder<R> keyMapper(KeyMapper keyMapper) {
        super.keyMapper(keyMapper);
        return this;
    }

    @Override
    public ProcessNodeBuilder<R> readableKeys(@NotNull Key<?>... keys) {
        super.readableKeys(keys);
        return this;
    }

    @Override
    public ProcessNodeBuilder<R> readableKeys(@NotNull Set<Key<?>> keys) {
        super.readableKeys(keys);
        return this;
    }

    @Override
    public ProcessNodeBuilder<R> writableKeys(@NotNull Key<?>... keys) {
        super.writableKeys(keys);
        return this;
    }

    @Override
    public ProcessNodeBuilder<R> writableKeys(@NotNull Set<Key<?>> keys) {
        super.writableKeys(keys);
        return this;
    }

    @Override
    public ProcessNodeBuilder<R> enabled(BooleanSupplier supplier) {
        super.enabled(supplier);
        return this;
    }

    @Override
    public ProcessNodeBuilder<R> wrap(@NotNull WrapperHandler<R> handler) {
        super.wrap(handler);
        return this;
    }

    @Override
    public ProcessNodeBuilder<R> wrap(@NotNull List<WrapperHandler<R>> wrapperHandlers) {
        super.wrap(wrapperHandlers);
        return this;
    }

    @Override
    public ProcessNodeBuilder<R> name(String name) {
        super.name(name);
        return this;
    }

    @Override
    public ProcessNodeBuilder<R> description(String description) {
        super.description(description);
        return this;
    }

    @Override
    public ProcessNodeBuilder<R> strongDependency() {
        super.strongDependency();
        return this;
    }

    @Override
    public ProcessNodeBuilder<R> weakDependency() {
        super.weakDependency();
        return this;
    }

    @Override
    public ProcessNodeBuilder<R> weakDependency(@NotNull BooleanSupplier supplier) {
        super.weakDependency(supplier);
        return this;
    }

    @Override
    public ProcessNodeBuilder<R> fallbackBy(Processor<R> fallbackProcessor) {
        super.fallbackBy(fallbackProcessor);
        return this;
    }

    @Override
    public Builder<ProcessNode<R>> id(String id) {
        super.id(id);
        return this;
    }

    @Override
    public ProcessNode<R> build() {
        if (processor != null) {
            processor = DefaultProcessorProxy.wrap(processor, handlers);
        }
        return super.build();
    }
}

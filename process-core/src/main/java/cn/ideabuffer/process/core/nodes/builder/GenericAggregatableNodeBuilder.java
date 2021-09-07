package cn.ideabuffer.process.core.nodes.builder;

import cn.ideabuffer.process.core.ProcessListener;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.ReturnCondition;
import cn.ideabuffer.process.core.aggregators.GenericAggregator;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.context.KeyMapper;
import cn.ideabuffer.process.core.nodes.GenericMergeableNode;
import cn.ideabuffer.process.core.nodes.Nodes;
import cn.ideabuffer.process.core.nodes.aggregate.GenericAggregatableNode;
import cn.ideabuffer.process.core.processors.AggregateProcessor;
import cn.ideabuffer.process.core.processors.impl.GenericAggregateProcessorImpl;
import cn.ideabuffer.process.core.processors.wrapper.WrapperHandler;
import cn.ideabuffer.process.core.processors.wrapper.proxy.AggregateProcessorProxy;
import cn.ideabuffer.process.core.rules.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;

/**
 * @author sangjian.sj
 * @date 2020/04/24
 */
public class GenericAggregatableNodeBuilder<P, R>
    extends
    AbstractExecutableNodeBuilder<R, AggregateProcessor<P, R>, GenericAggregatableNode<P, R>, WrapperHandler<R>> {

    private List<GenericMergeableNode<P>> mergeableNodes;

    private GenericAggregator<P, R> aggregator;

    private GenericAggregatableNodeBuilder(GenericAggregatableNode<P, R> node) {
        super(node);
        mergeableNodes = new ArrayList<>();
    }

    public static <P, R> GenericAggregatableNodeBuilder<P, R> newBuilder() {
        GenericAggregatableNode<P, R> node = Nodes.newGenericAggregatableNode();
        return new GenericAggregatableNodeBuilder<>(node);
    }

    @Override
    public GenericAggregatableNodeBuilder<P, R> parallel() {
        super.parallel();
        return this;
    }

    @Override
    public GenericAggregatableNodeBuilder<P, R> parallel(Executor executor) {
        super.parallel(executor);
        return this;
    }

    @Override
    public GenericAggregatableNodeBuilder<P, R> processOn(Rule rule) {
        super.processOn(rule);
        return this;
    }

    @Override
    public GenericAggregatableNodeBuilder<P, R> addListeners(ProcessListener<R>... listeners) {
        super.addListeners(listeners);
        return this;
    }

    public GenericAggregatableNodeBuilder<P, R> aggregate(@NotNull GenericMergeableNode<P>... nodes) {
        this.mergeableNodes.addAll(Arrays.asList(nodes));
        return this;
    }

    public GenericAggregatableNodeBuilder<P, R> aggregate(@NotNull List<GenericMergeableNode<P>> nodes) {
        this.mergeableNodes.addAll(nodes);
        return this;
    }

    public GenericAggregatableNodeBuilder<P, R> aggregator(@NotNull GenericAggregator<P, R> aggregator) {
        this.aggregator = aggregator;
        return this;
    }

    @Override
    public GenericAggregatableNodeBuilder<P, R> by(AggregateProcessor<P, R> processor) {
        super.by(processor);
        return this;
    }

    @Override
    public GenericAggregatableNodeBuilder<P, R> resultKey(Key<R> resultKey) {
        super.resultKey(resultKey);
        return this;
    }

    @Override
    public GenericAggregatableNodeBuilder<P, R> returnOn(ReturnCondition<R> condition) {
        super.returnOn(condition);
        return this;
    }

    @Override
    public GenericAggregatableNodeBuilder<P, R> enabled(BooleanSupplier supplier) {
        super.enabled(supplier);
        return this;
    }

    @Override
    public GenericAggregatableNodeBuilder<P, R> wrap(@NotNull WrapperHandler<R> handler) {
        super.wrap(handler);
        return this;
    }

    @Override
    public GenericAggregatableNodeBuilder<P, R> wrap(@NotNull List<WrapperHandler<R>> handlers) {
        super.wrap(handlers);
        return this;
    }

    @Override
    public GenericAggregatableNodeBuilder<P, R> name(String name) {
        super.name(name);
        return this;
    }

    @Override
    public GenericAggregatableNodeBuilder<P, R> description(String description) {
        super.description(description);
        return this;
    }

    @Override
    public GenericAggregatableNodeBuilder<P, R> keyMapper(KeyMapper keyMapper) {
        super.keyMapper(keyMapper);
        return this;
    }

    @Override
    public GenericAggregatableNodeBuilder<P, R> readableKeys(@NotNull Key<?>... keys) {
        super.readableKeys(keys);
        return this;
    }

    @Override
    public GenericAggregatableNodeBuilder<P, R> readableKeys(@NotNull Set<Key<?>> keys) {
        super.readableKeys(keys);
        return this;
    }

    @Override
    public GenericAggregatableNodeBuilder<P, R> strongDependency() {
        super.strongDependency();
        return this;
    }

    @Override
    public GenericAggregatableNodeBuilder<P, R> weakDependency() {
        super.weakDependency();
        return this;
    }

    @Override
    public GenericAggregatableNodeBuilder<P, R> weakDependency(@NotNull BooleanSupplier supplier) {
        super.weakDependency(supplier);
        return this;
    }

    @Override
    public GenericAggregatableNodeBuilder<P, R> fallbackProcessor(Processor<R> fallbackProcessor) {
        super.fallbackProcessor(fallbackProcessor);
        return this;
    }

    @Override
    public GenericAggregatableNode<P, R> build() {
        if (processor == null) {
            processor = new GenericAggregateProcessorImpl<>();
        }
        processor = AggregateProcessorProxy.wrap(processor, handlers);
        GenericAggregatableNode<P, R> node = super.build();
        processor.aggregator(aggregator);
        processor.aggregate(mergeableNodes);
        return node;
    }
}

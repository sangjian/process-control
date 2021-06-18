package cn.ideabuffer.process.core.nodes.builder;

import cn.ideabuffer.process.core.ProcessListener;
import cn.ideabuffer.process.core.ReturnCondition;
import cn.ideabuffer.process.core.aggregator.UnitAggregator;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.nodes.GenericMergeableNode;
import cn.ideabuffer.process.core.nodes.Nodes;
import cn.ideabuffer.process.core.nodes.aggregate.UnitAggregatableNode;
import cn.ideabuffer.process.core.processors.AggregateProcessor;
import cn.ideabuffer.process.core.processors.impl.UnitAggregateProcessorImpl;
import cn.ideabuffer.process.core.processors.wrapper.WrapperHandler;
import cn.ideabuffer.process.core.processors.wrapper.proxy.AggregateProcessorProxy;
import cn.ideabuffer.process.core.rule.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;

/**
 * @author sangjian.sj
 * @date 2020/04/24
 */
public class UnitAggregatableNodeBuilder<R>
    extends AbstractExecutableNodeBuilder<R, AggregateProcessor<R, R>, UnitAggregatableNode<R>> {

    private List<GenericMergeableNode<R>> mergeableNodes;

    private UnitAggregator<R> aggregator;

    private UnitAggregatableNodeBuilder(UnitAggregatableNode<R> node) {
        super(node);
        mergeableNodes = new ArrayList<>();
    }

    public static <R> UnitAggregatableNodeBuilder<R> newBuilder() {
        UnitAggregatableNode<R> node = Nodes.newUnitAggregatableNode();
        return new UnitAggregatableNodeBuilder<>(node);
    }

    @Override
    public UnitAggregatableNodeBuilder<R> parallel() {
        super.parallel();
        return this;
    }

    @Override
    public UnitAggregatableNodeBuilder<R> parallel(Executor executor) {
        super.parallel(executor);
        return this;
    }

    @Override
    public UnitAggregatableNodeBuilder<R> processOn(Rule rule) {
        super.processOn(rule);
        return this;
    }

    @Override
    public UnitAggregatableNodeBuilder<R> addListeners(ProcessListener<R>... listeners) {
        super.addListeners(listeners);
        return this;
    }

    @Override
    public UnitAggregatableNodeBuilder<R> by(AggregateProcessor<R, R> processor) {
        super.by(processor);
        return this;
    }

    public UnitAggregatableNodeBuilder<R> aggregate(@NotNull GenericMergeableNode<R>... nodes) {
        this.mergeableNodes.addAll(Arrays.asList(nodes));
        return this;
    }

    public UnitAggregatableNodeBuilder<R> aggregate(@NotNull List<GenericMergeableNode<R>> nodes) {
        this.mergeableNodes.addAll(nodes);
        return this;
    }

    public UnitAggregatableNodeBuilder<R> aggregator(@NotNull UnitAggregator<R> aggregator) {
        this.aggregator = aggregator;
        return this;
    }

    @Override
    public UnitAggregatableNodeBuilder<R> resultKey(Key<R> resultKey) {
        super.resultKey(resultKey);
        return this;
    }

    @Override
    public UnitAggregatableNodeBuilder<R> returnOn(ReturnCondition<R> condition) {
        super.returnOn(condition);
        return this;
    }

    @Override
    public UnitAggregatableNodeBuilder<R> enabled(BooleanSupplier supplier) {
        super.enabled(supplier);
        return this;
    }

    @Override
    public UnitAggregatableNodeBuilder<R> wrap(@NotNull WrapperHandler<R>... handlers) {
        super.wrap(handlers);
        return this;
    }

    @Override
    public UnitAggregatableNodeBuilder<R> wrap(@NotNull List<WrapperHandler<R>> handlers) {
        super.wrap(handlers);
        return this;
    }

    @Override
    public UnitAggregatableNode<R> build() {
        if (processor == null) {
            processor = new UnitAggregateProcessorImpl<>();
        }
        processor = AggregateProcessorProxy.wrap(processor, handlers);
        UnitAggregatableNode<R> node = super.build();
        processor.aggregate(mergeableNodes);
        processor.aggregator(aggregator);
        return node;
    }
}

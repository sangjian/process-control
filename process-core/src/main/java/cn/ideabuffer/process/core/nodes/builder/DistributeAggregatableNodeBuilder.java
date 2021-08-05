package cn.ideabuffer.process.core.nodes.builder;

import cn.ideabuffer.process.core.ProcessListener;
import cn.ideabuffer.process.core.ReturnCondition;
import cn.ideabuffer.process.core.aggregators.DistributeAggregator;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.nodes.DistributeMergeableNode;
import cn.ideabuffer.process.core.nodes.Nodes;
import cn.ideabuffer.process.core.nodes.aggregate.DistributeAggregatableNode;
import cn.ideabuffer.process.core.processors.DistributeAggregateProcessor;
import cn.ideabuffer.process.core.processors.impl.DistributeAggregateProcessorImpl;
import cn.ideabuffer.process.core.processors.wrapper.WrapperHandler;
import cn.ideabuffer.process.core.processors.wrapper.proxy.DistributeAggregateProcessorProxy;
import cn.ideabuffer.process.core.rules.Rule;
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
public class DistributeAggregatableNodeBuilder<R>
    extends
    AbstractExecutableNodeBuilder<R, DistributeAggregateProcessor<R>, DistributeAggregatableNode<R>,
        WrapperHandler<R>> {

    private List<DistributeMergeableNode<?, R>> mergeableNodes;

    private DistributeAggregator<R> aggregator;

    private DistributeAggregatableNodeBuilder(DistributeAggregatableNode<R> node) {
        super(node);
        mergeableNodes = new ArrayList<>();
    }

    public static <R> DistributeAggregatableNodeBuilder<R> newBuilder() {
        DistributeAggregatableNode<R> node = Nodes.newDistributeAggregatableNode();
        return new DistributeAggregatableNodeBuilder<>(node);
    }

    @Override
    public DistributeAggregatableNodeBuilder<R> parallel() {
        super.parallel();
        return this;
    }

    @Override
    public DistributeAggregatableNodeBuilder<R> parallel(Executor executor) {
        super.parallel(executor);
        return this;
    }

    @Override
    public DistributeAggregatableNodeBuilder<R> processOn(Rule rule) {
        super.processOn(rule);
        return this;
    }

    @Override
    public DistributeAggregatableNodeBuilder<R> addListeners(ProcessListener<R>... listeners) {
        super.addListeners(listeners);
        return this;
    }

    @Override
    public DistributeAggregatableNodeBuilder<R> by(DistributeAggregateProcessor<R> processor) {
        super.by(processor);
        return this;
    }

    public DistributeAggregatableNodeBuilder<R> aggregate(@NotNull DistributeMergeableNode<?, R>... nodes) {
        this.mergeableNodes.addAll(Arrays.asList(nodes));
        return this;
    }

    public DistributeAggregatableNodeBuilder<R> aggregate(@NotNull List<DistributeMergeableNode<?, R>> nodes) {
        this.mergeableNodes.addAll(nodes);
        return this;
    }

    public DistributeAggregatableNodeBuilder<R> aggregator(@NotNull DistributeAggregator<R> aggregator) {
        this.aggregator = aggregator;
        return this;
    }

    @Override
    public DistributeAggregatableNodeBuilder<R> resultKey(Key<R> resultKey) {
        super.resultKey(resultKey);
        return this;
    }

    @Override
    public DistributeAggregatableNodeBuilder<R> returnOn(ReturnCondition<R> condition) {
        super.returnOn(condition);
        return this;
    }

    @Override
    public DistributeAggregatableNodeBuilder<R> enabled(BooleanSupplier supplier) {
        super.enabled(supplier);
        return this;
    }

    @Override
    public DistributeAggregatableNodeBuilder<R> wrap(@NotNull WrapperHandler<R> handler) {
        super.wrap(handler);
        return this;
    }

    @Override
    public DistributeAggregatableNodeBuilder<R> wrap(@NotNull List<WrapperHandler<R>> wrapperHandlers) {
        super.wrap(wrapperHandlers);
        return this;
    }

    @Override
    public DistributeAggregatableNode<R> build() {
        if (aggregator == null) {
            throw new NullPointerException("aggregator cannot be null!");
        }
        if (processor == null) {
            processor = new DistributeAggregateProcessorImpl<>();
        }
        processor = DistributeAggregateProcessorProxy.wrap(processor, handlers);
        DistributeAggregatableNode<R> node = super.build();
        processor.aggregate(mergeableNodes);
        processor.aggregator(aggregator);
        return node;
    }
}

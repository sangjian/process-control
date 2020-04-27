package cn.ideabuffer.process.core.nodes.builder;

import cn.ideabuffer.process.core.aggregator.DistributeAggregator;
import cn.ideabuffer.process.core.handler.ExceptionHandler;
import cn.ideabuffer.process.core.nodes.DistributeMergeableNode;
import cn.ideabuffer.process.core.nodes.Nodes;
import cn.ideabuffer.process.core.nodes.aggregate.DistributeAggregatableNode;
import cn.ideabuffer.process.core.rule.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/04/24
 */
public class DistributeAggregatableNodeBuilder<R> extends AbstractExecutableNodeBuilder<DistributeAggregatableNode<R>> {

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
    public DistributeAggregatableNodeBuilder<R> exceptionHandler(ExceptionHandler handler) {
        super.exceptionHandler(handler);
        return this;
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

    public DistributeAggregatableNodeBuilder<R> aggregate(@NotNull DistributeMergeableNode<?, R>... nodes) {
        this.mergeableNodes.addAll(Arrays.asList(nodes));
        return this;
    }

    public DistributeAggregatableNodeBuilder<R> aggregator(@NotNull DistributeAggregator<R> aggregator) {
        this.aggregator = aggregator;
        return this;
    }

    @Override
    public DistributeAggregatableNode<R> build() {
        DistributeAggregatableNode<R> node = super.build();
        node.aggregate(mergeableNodes);
        node.aggregator(aggregator);
        return node;
    }
}

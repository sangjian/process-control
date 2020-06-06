package cn.ideabuffer.process.core.processors.impl;

import cn.ideabuffer.process.core.aggregator.GenericAggregator;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.GenericMergeableNode;
import cn.ideabuffer.process.core.processors.AggregateProcessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/04/30
 */
public class DefaultAggregateProcessor<I, O> implements AggregateProcessor<I, O> {

    private GenericAggregator<I, O> aggregator;
    private List<GenericMergeableNode<I>> mergeableNodes;

    public DefaultAggregateProcessor() {
    }

    public DefaultAggregateProcessor(@NotNull GenericAggregator<I, O> aggregator,
        List<GenericMergeableNode<I>> mergeableNodes) {
        this.aggregator = aggregator;
        this.mergeableNodes = mergeableNodes;
    }

    @Override
    public GenericAggregator<I, O> getAggregator() {
        return aggregator;
    }

    public void setAggregator(@NotNull GenericAggregator<I, O> aggregator) {
        this.aggregator = aggregator;
    }

    @Override
    public List<GenericMergeableNode<I>> getMergeableNodes() {
        return mergeableNodes;
    }

    public void setMergeableNodes(List<GenericMergeableNode<I>> mergeableNodes) {
        this.mergeableNodes = mergeableNodes;
    }

    @Override
    public void aggregate(@NotNull List<GenericMergeableNode<I>> nodes) {
        this.mergeableNodes = nodes;
    }

    @Override
    public void aggregator(@NotNull GenericAggregator<I, O> aggregator) {
        this.aggregator = aggregator;
    }

    @Nullable
    @Override
    public O process(@NotNull Context context) throws Exception {
        return aggregator.aggregate(context, mergeableNodes);
    }
}

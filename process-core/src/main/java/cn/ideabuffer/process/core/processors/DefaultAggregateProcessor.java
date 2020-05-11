package cn.ideabuffer.process.core.processors;

import cn.ideabuffer.process.core.aggregator.Aggregator;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.MergeableNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/04/30
 */
public class DefaultAggregateProcessor<I, O> implements AggregateProcessor<I, O> {

    private Aggregator<I, O> aggregator;
    private List<MergeableNode<I>> mergeableNodes;

    public DefaultAggregateProcessor() {
    }

    public DefaultAggregateProcessor(@NotNull Aggregator<I, O> aggregator, List<MergeableNode<I>> mergeableNodes) {
        this.aggregator = aggregator;
        this.mergeableNodes = mergeableNodes;
    }

    @Override
    public Aggregator<I, O> getAggregator() {
        return aggregator;
    }

    public void setAggregator(@NotNull Aggregator<I, O> aggregator) {
        this.aggregator = aggregator;
    }

    @Override
    public List<MergeableNode<I>> getMergeableNodes() {
        return mergeableNodes;
    }

    public void setMergeableNodes(List<MergeableNode<I>> mergeableNodes) {
        this.mergeableNodes = mergeableNodes;
    }

    @Override
    public void aggregate(@NotNull List<MergeableNode<I>> nodes) {
        this.mergeableNodes = nodes;
    }

    @Override
    public void aggregator(@NotNull Aggregator<I, O> aggregator) {
        this.aggregator = aggregator;
    }

    @Nullable
    @Override
    public O process(@NotNull Context context) throws Exception{
        return aggregator.aggregate(context, mergeableNodes);
    }
}

package cn.ideabuffer.process.core.processors;

import cn.ideabuffer.process.core.aggregator.Aggregator;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.MergeableNode;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/04/30
 */
public class DefaultAggregateProcessor<A extends Aggregator<List<N>, R>, N extends MergeableNode<?>, R> implements AggregateProcessor<A, N, R> {

    private A aggregator;
    private List<N> mergeableNodes;

    public DefaultAggregateProcessor() {
    }

    public DefaultAggregateProcessor(@NotNull A aggregator, List<N> mergeableNodes) {
        this.aggregator = aggregator;
        this.mergeableNodes = mergeableNodes;
    }

    @Override
    public A getAggregator() {
        return aggregator;
    }

    public void setAggregator(@NotNull A aggregator) {
        this.aggregator = aggregator;
    }

    @Override
    public List<N> getMergeableNodes() {
        return mergeableNodes;
    }

    public void setMergeableNodes(List<N> mergeableNodes) {
        this.mergeableNodes = mergeableNodes;
    }

    @Override
    public void aggregate(@NotNull List<N> nodes) {
        this.mergeableNodes = nodes;
    }

    @Override
    public void aggregator(@NotNull A aggregator) {
        this.aggregator = aggregator;
    }

    @Override
    public R process(@NotNull Context context) throws Exception{
        return aggregator.aggregate(context, mergeableNodes);
    }
}

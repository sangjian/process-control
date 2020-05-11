package cn.ideabuffer.process.core.processors;

import cn.ideabuffer.process.core.aggregator.DistributeAggregator;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.DistributeMergeableNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/05/02
 */
public class DistributeAggregateProcessorImpl<O> implements DistributeAggregateProcessor<O> {

    private DistributeAggregator<O> aggregator;
    private List<DistributeMergeableNode<?, O>> mergeableNodes;

    @Override
    public void aggregate(@NotNull List<DistributeMergeableNode<?, O>> distributeMergeableNodes) {
        this.mergeableNodes = distributeMergeableNodes;
    }

    @Override
    public void aggregator(@NotNull DistributeAggregator<O> aggregator) {
        this.aggregator = aggregator;
    }

    @Override
    public DistributeAggregator<O> getAggregator() {
        return aggregator;
    }

    @Override
    public List<DistributeMergeableNode<?, O>> getMergeableNodes() {
        return mergeableNodes;
    }

    @Nullable
    @Override
    public O process(@NotNull Context context) throws Exception {
        return aggregator.aggregate(context, mergeableNodes);
    }
}

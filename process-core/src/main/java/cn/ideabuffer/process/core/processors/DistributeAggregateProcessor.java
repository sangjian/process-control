package cn.ideabuffer.process.core.processors;

import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.aggregator.DistributeAggregator;
import cn.ideabuffer.process.core.nodes.DistributeMergeableNode;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/04/30
 */
public interface DistributeAggregateProcessor<O> extends Processor<O> {

    void aggregate(@NotNull List<DistributeMergeableNode<?, O>> nodes);

    void aggregator(@NotNull DistributeAggregator<O> aggregator);

    DistributeAggregator<O> getAggregator();

    List<DistributeMergeableNode<?, O>> getMergeableNodes();
}

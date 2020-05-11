package cn.ideabuffer.process.core.processors.impl;

import cn.ideabuffer.process.core.aggregator.Aggregator;
import cn.ideabuffer.process.core.nodes.MergeableNode;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/05/02
 */
public class UnitAggregateProcessorImpl<R> extends DefaultAggregateProcessor<R, R> {
    public UnitAggregateProcessorImpl() {
    }

    public UnitAggregateProcessorImpl(
        @NotNull Aggregator<R, R> aggregator,
        List<MergeableNode<R>> mergeableNodes) {
        super(aggregator, mergeableNodes);
    }
}

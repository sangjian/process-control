package cn.ideabuffer.process.core.processors.impl;

import cn.ideabuffer.process.core.aggregator.UnitAggregator;
import cn.ideabuffer.process.core.nodes.GenericMergeableNode;
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
        @NotNull UnitAggregator<R> aggregator,
        List<GenericMergeableNode<R>> mergeableNodes) {
        super(aggregator, mergeableNodes);
    }
}

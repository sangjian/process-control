package cn.ideabuffer.process.core.processors.impl;

import cn.ideabuffer.process.core.aggregator.GenericAggregator;
import cn.ideabuffer.process.core.nodes.GenericMergeableNode;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/05/02
 */
public class GenericAggregateProcessorImpl<I, O> extends DefaultAggregateProcessor<I, O> {
    public GenericAggregateProcessorImpl() {
    }

    public GenericAggregateProcessorImpl(
        @NotNull GenericAggregator<I, O> aggregator,
        List<GenericMergeableNode<I>> mergeableNodes) {
        super(aggregator, mergeableNodes);
    }
}

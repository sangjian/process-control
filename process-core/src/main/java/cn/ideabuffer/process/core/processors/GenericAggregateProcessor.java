package cn.ideabuffer.process.core.processors;

import cn.ideabuffer.process.core.aggregator.GenericAggregator;
import cn.ideabuffer.process.core.nodes.MergeableNode;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/05/02
 */
public class GenericAggregateProcessor<P, R> extends DefaultAggregateProcessor<GenericAggregator<P, R>, MergeableNode<P>, R> {
    public GenericAggregateProcessor() {
    }

    public GenericAggregateProcessor(
        @NotNull GenericAggregator<P, R> aggregator,
        List<MergeableNode<P>> mergeableNodes) {
        super(aggregator, mergeableNodes);
    }
}

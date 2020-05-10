package cn.ideabuffer.process.core.processors;

import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.aggregator.Aggregator;
import cn.ideabuffer.process.core.nodes.MergeNode;
import cn.ideabuffer.process.core.nodes.MergeableNode;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/05/02
 */
public interface AggregateProcessor<I, O> extends Processor<O> {

    void aggregate(@NotNull List<MergeableNode<I>> nodes);

    void aggregator(@NotNull Aggregator<I, O> aggregator);

    Aggregator<I, O> getAggregator();

    List<MergeableNode<I>> getMergeableNodes();

}

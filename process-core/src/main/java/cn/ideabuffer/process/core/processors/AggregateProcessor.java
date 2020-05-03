package cn.ideabuffer.process.core.processors;

import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.aggregator.Aggregator;
import cn.ideabuffer.process.core.nodes.MergeableNode;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/05/02
 */
public interface AggregateProcessor<A extends Aggregator<List<N>, R>, N extends MergeableNode<?>, R> extends Processor<R> {

    void aggregate(@NotNull List<N> nodes);

    void aggregator(@NotNull A aggregator);

    A getAggregator();

    List<N> getMergeableNodes();

}

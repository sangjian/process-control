package cn.ideabuffer.process.nodes;

import cn.ideabuffer.process.Parallelizable;
import cn.ideabuffer.process.handler.ExceptionHandler;
import cn.ideabuffer.process.nodes.aggregate.Aggregator;
import cn.ideabuffer.process.nodes.merger.Merger;
import cn.ideabuffer.process.rule.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface AggregatableNode<R> extends TransmittableNode<R>, Parallelizable {

    AggregatableNode<R> merge(@NotNull MergeableNode<R>... nodes);

    AggregatableNode<R> by(@NotNull Merger<R> merger);

    @Override
    AggregatableNode<R> parallel();

    @Override
    AggregatableNode<R> parallel(Executor executor);

    @Override
    AggregatableNode<R> processOn(Rule rule);

    AggregatableNode<R> aggregator(@NotNull Aggregator aggregator);

    Aggregator getAggregator();

    Merger<R> getMerger();

    List<MergeableNode<R>> getMergeableNodes();
}

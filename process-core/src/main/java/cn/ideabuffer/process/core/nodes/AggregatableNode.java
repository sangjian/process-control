package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.Parallelizable;
import cn.ideabuffer.process.core.aggregator.Aggregator;
import cn.ideabuffer.process.core.rule.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * 可聚合结果的节点
 *
 * @param <A> 聚合器类型
 * @param <N> 合并节点类型
 * @param <R> 聚合结果类型
 * {@inheritDoc}
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface AggregatableNode<A extends Aggregator<List<N>, R>, N extends MergeableNode<?>, R>
    extends TransmittableNode<R>, Parallelizable {

    AggregatableNode<A, N, R> aggregate(@NotNull N... nodes);

    AggregatableNode<A, N, R> aggregator(@NotNull A aggregator);

    @Override
    AggregatableNode<A, N, R> parallel();

    @Override
    AggregatableNode<A, N, R> parallel(Executor executor);

    @Override
    AggregatableNode<A, N, R> processOn(Rule rule);

    A getAggregator();

    List<N> getMergeableNodes();

}

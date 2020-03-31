package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.Parallelizable;
import cn.ideabuffer.process.core.nodes.aggregate.Aggregator;
import cn.ideabuffer.process.core.rule.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * 可聚合结果的节点
 *
 * @param <N> 合并节点类型
 * @param <R> 聚合结果类型
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface AggregatableNode<N extends MergeableNode<?>, R> extends TransmittableNode<R>, Parallelizable {

    AggregatableNode<N, R> aggregate(@NotNull N... nodes);

    @Override
    AggregatableNode<N, R> parallel();

    @Override
    AggregatableNode<N, R> parallel(Executor executor);

    @Override
    AggregatableNode<N, R> processOn(Rule rule);

    Aggregator<List<N>, R> getAggregator();

}

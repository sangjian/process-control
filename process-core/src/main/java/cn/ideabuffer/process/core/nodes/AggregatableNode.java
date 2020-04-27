package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.Parallelizable;
import cn.ideabuffer.process.core.aggregator.Aggregator;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 可聚合结果的节点
 *
 * @param <A> 聚合器类型
 * @param <N> 合并节点类型
 * @param <R> 聚合结果类型 {@inheritDoc}
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface AggregatableNode<A extends Aggregator<List<N>, R>, N extends MergeableNode<?>, R>
    extends TransmittableNode<R>, Parallelizable {

    void aggregate(@NotNull List<N> nodes);

    void aggregator(@NotNull A aggregator);

    A getAggregator();

    List<N> getMergeableNodes();

}

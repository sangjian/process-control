package cn.ideabuffer.process.nodes;

import cn.ideabuffer.process.nodes.aggregate.UnitAggregator;
import cn.ideabuffer.process.rule.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * 可聚合结果的节点
 *
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface UnitAggregatableNode<R> extends AggregatableNode<MergeableNode<R>, R> {

    @Override
    UnitAggregatableNode<R> aggregate(@NotNull MergeableNode<R>... nodes);

    @Override
    UnitAggregatableNode<R> parallel();

    @Override
    UnitAggregatableNode<R> parallel(Executor executor);

    @Override
    UnitAggregatableNode<R> processOn(Rule rule);

    UnitAggregatableNode<R> aggregator(@NotNull UnitAggregator<R> aggregator);

    List<MergeableNode<R>> getMergeableNodes();

    @Override
    UnitAggregator<R> getAggregator();
}

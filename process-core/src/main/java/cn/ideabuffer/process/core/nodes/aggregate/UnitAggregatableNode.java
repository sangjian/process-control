package cn.ideabuffer.process.core.nodes.aggregate;

import cn.ideabuffer.process.core.aggregator.UnitAggregator;
import cn.ideabuffer.process.core.nodes.AggregatableNode;
import cn.ideabuffer.process.core.nodes.MergeableNode;
import cn.ideabuffer.process.core.rule.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * 可聚合结果的节点
 *
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface UnitAggregatableNode<R> extends AggregatableNode<UnitAggregator<R>, MergeableNode<R>, R> {

    @Override
    UnitAggregatableNode<R> aggregate(@NotNull MergeableNode<R>... nodes);

    @Override
    UnitAggregatableNode<R> parallel();

    @Override
    UnitAggregatableNode<R> parallel(Executor executor);

    @Override
    UnitAggregatableNode<R> processOn(Rule rule);

    @Override
    UnitAggregatableNode<R> aggregator(@NotNull UnitAggregator<R> aggregator);

    @Override
    List<MergeableNode<R>> getMergeableNodes();

}

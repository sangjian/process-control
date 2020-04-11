package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.nodes.aggregate.GenericAggregator;
import cn.ideabuffer.process.core.nodes.aggregate.UnitAggregator;
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
public interface GenericAggregatableNode<P, R> extends AggregatableNode<MergeableNode<P>, R> {

    @Override
    GenericAggregatableNode<P, R> aggregate(@NotNull MergeableNode<P>... nodes);

    @Override
    GenericAggregatableNode<P, R> parallel();

    @Override
    GenericAggregatableNode<P, R> parallel(Executor executor);

    @Override
    GenericAggregatableNode<P, R> processOn(Rule rule);

    GenericAggregatableNode<P, R> aggregator(@NotNull GenericAggregator<P, R> aggregator);

    List<MergeableNode<P>> getMergeableNodes();

    @Override
    GenericAggregator<P, R> getAggregator();
}

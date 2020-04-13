package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.nodes.aggregate.DistributeAggregator;
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
public interface DistributeAggregatableNode<R> extends AggregatableNode<DistributeAggregator<R>, DistributeMergeableNode<?, R>, R> {

    @Override
    DistributeAggregatableNode<R> aggregate(@NotNull DistributeMergeableNode<?, R>... nodes);

    @Override
    DistributeAggregatableNode<R> parallel();

    @Override
    DistributeAggregatableNode<R> parallel(Executor executor);

    @Override
    DistributeAggregatableNode<R> processOn(Rule rule);

    @Override
    List<DistributeMergeableNode<?, R>> getMergeableNodes();

}

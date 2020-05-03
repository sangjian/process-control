package cn.ideabuffer.process.core.nodes.aggregate;

import cn.ideabuffer.process.core.aggregator.DistributeAggregator;
import cn.ideabuffer.process.core.nodes.AggregatableNode;
import cn.ideabuffer.process.core.nodes.DistributeMergeableNode;
import cn.ideabuffer.process.core.processors.AggregateProcessor;
import cn.ideabuffer.process.core.processors.DistributeAggregateProcessor;

/**
 * 可聚合结果的节点
 *
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface DistributeAggregatableNode<R>
    extends
    AggregatableNode<DistributeAggregator<R>, DistributeMergeableNode<?, R>, R,
        DistributeAggregateProcessor<R>> {

}

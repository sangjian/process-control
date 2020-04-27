package cn.ideabuffer.process.core.nodes.aggregate;

import cn.ideabuffer.process.core.aggregator.UnitAggregator;
import cn.ideabuffer.process.core.nodes.AggregatableNode;
import cn.ideabuffer.process.core.nodes.MergeableNode;

/**
 * 可聚合结果的节点
 * <p>
 * {@inheritDoc}
 *
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface UnitAggregatableNode<R> extends AggregatableNode<UnitAggregator<R>, MergeableNode<R>, R> {

}

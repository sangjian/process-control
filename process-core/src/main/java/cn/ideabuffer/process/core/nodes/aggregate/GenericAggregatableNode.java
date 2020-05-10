package cn.ideabuffer.process.core.nodes.aggregate;

import cn.ideabuffer.process.core.aggregator.GenericAggregator;
import cn.ideabuffer.process.core.nodes.AggregatableNode;
import cn.ideabuffer.process.core.nodes.MergeableNode;
import cn.ideabuffer.process.core.processors.GenericAggregateProcessorImpl;

/**
 * 可聚合结果的节点
 *
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface GenericAggregatableNode<P, R> extends
    AggregatableNode<P, R> {

}

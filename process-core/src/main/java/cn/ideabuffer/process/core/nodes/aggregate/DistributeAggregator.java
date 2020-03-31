package cn.ideabuffer.process.core.nodes.aggregate;

import cn.ideabuffer.process.core.nodes.DistributeMergeableNode;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/03/27
 */
public interface DistributeAggregator<R> extends Aggregator<List<DistributeMergeableNode<?, R>>, R> {

}

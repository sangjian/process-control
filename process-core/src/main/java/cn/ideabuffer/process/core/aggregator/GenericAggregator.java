package cn.ideabuffer.process.core.aggregator;

import cn.ideabuffer.process.core.nodes.MergeableNode;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/03/28
 */
public interface GenericAggregator<I, O> extends Aggregator<List<MergeableNode<I>>, O> {

}

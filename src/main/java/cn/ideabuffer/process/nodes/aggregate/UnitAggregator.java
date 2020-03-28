package cn.ideabuffer.process.nodes.aggregate;

import cn.ideabuffer.process.nodes.MergeableNode;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/03/28
 */
public interface UnitAggregator<R> extends Aggregator<List<MergeableNode<R>>, R> {

}

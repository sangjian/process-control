package cn.ideabuffer.process.nodes.aggregate;

import cn.ideabuffer.process.context.Context;
import cn.ideabuffer.process.nodes.DistributeMergeableNode;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/03/27
 */
public interface DistributeAggregator<R> extends Aggregator<List<DistributeMergeableNode<?, R>>, R> {

    @Override
    R aggregate(Context context, List<DistributeMergeableNode<?, R>> mergeableNodes) throws Exception;
}

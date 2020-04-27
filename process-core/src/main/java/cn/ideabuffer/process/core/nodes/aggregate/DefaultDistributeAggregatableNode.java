package cn.ideabuffer.process.core.nodes.aggregate;

import cn.ideabuffer.process.core.aggregator.DistributeAggregator;
import cn.ideabuffer.process.core.nodes.DistributeMergeableNode;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/03/10
 */
public class DefaultDistributeAggregatableNode<R>
    extends AbstractAggregatableNode<DistributeAggregator<R>, DistributeMergeableNode<?, R>, R> implements
    DistributeAggregatableNode<R> {

    public DefaultDistributeAggregatableNode() {
        this(null, null);
    }

    public DefaultDistributeAggregatableNode(DistributeAggregator<R> aggregator) {
        this(aggregator, null);
    }

    public DefaultDistributeAggregatableNode(List<DistributeMergeableNode<?, R>> mergeableNodes) {
        this(null, mergeableNodes);
    }

    public DefaultDistributeAggregatableNode(DistributeAggregator<R> aggregator,
        List<DistributeMergeableNode<?, R>> mergeableNodes) {
        super(aggregator, mergeableNodes);
    }

}

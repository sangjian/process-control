package cn.ideabuffer.process.core.nodes.aggregate;

import cn.ideabuffer.process.core.aggregator.GenericAggregator;
import cn.ideabuffer.process.core.nodes.MergeableNode;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/03/10
 */
public class DefaultGenericAggregatableNode<P, R>
    extends AbstractAggregatableNode<GenericAggregator<P, R>, MergeableNode<P>, R> implements
    GenericAggregatableNode<P, R> {

    public DefaultGenericAggregatableNode() {
        this(null, null);
    }

    public DefaultGenericAggregatableNode(GenericAggregator<P, R> aggregator) {
        this(aggregator, null);
    }

    public DefaultGenericAggregatableNode(List<MergeableNode<P>> mergeableNodes) {
        this(null, mergeableNodes);
    }

    public DefaultGenericAggregatableNode(GenericAggregator<P, R> aggregator, List<MergeableNode<P>> mergeableNodes) {
        super(aggregator, mergeableNodes);
    }
}

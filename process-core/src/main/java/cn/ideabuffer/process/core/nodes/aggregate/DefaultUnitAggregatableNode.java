package cn.ideabuffer.process.core.nodes.aggregate;

import cn.ideabuffer.process.core.aggregator.UnitAggregator;
import cn.ideabuffer.process.core.nodes.MergeableNode;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/03/10
 */
public class DefaultUnitAggregatableNode<R> extends AbstractAggregatableNode<UnitAggregator<R>, MergeableNode<R>, R>
    implements UnitAggregatableNode<R> {

    public DefaultUnitAggregatableNode() {
        this(null, null);
    }

    public DefaultUnitAggregatableNode(UnitAggregator<R> aggregator) {
        this(aggregator, null);
    }

    public DefaultUnitAggregatableNode(List<MergeableNode<R>> mergeableNodes) {
        this(null, mergeableNodes);
    }

    public DefaultUnitAggregatableNode(UnitAggregator<R> aggregator, List<MergeableNode<R>> mergeableNodes) {
        super(aggregator, mergeableNodes);
    }

}

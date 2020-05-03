package cn.ideabuffer.process.core.nodes.aggregate;

import cn.ideabuffer.process.core.aggregator.DistributeAggregator;
import cn.ideabuffer.process.core.nodes.DistributeMergeableNode;
import cn.ideabuffer.process.core.processors.DistributeAggregateProcessor;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/03/10
 */
public class DefaultDistributeAggregatableNode<R>
    extends AbstractAggregatableNode<DistributeAggregator<R>, DistributeMergeableNode<?, R>, R, DistributeAggregateProcessor<R>> implements
    DistributeAggregatableNode<R> {

    public DefaultDistributeAggregatableNode() {
        this(new DistributeAggregateProcessor<>());
    }

    public DefaultDistributeAggregatableNode(DistributeAggregateProcessor<R> processor) {
        super(processor);
    }

}

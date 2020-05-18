package cn.ideabuffer.process.core.nodes.aggregate;

import cn.ideabuffer.process.core.nodes.DistributeAggregatableNode;
import cn.ideabuffer.process.core.processors.impl.DistributeAggregateProcessorImpl;

/**
 * @author sangjian.sj
 * @date 2020/03/10
 */
public class DefaultDistributeAggregatableNode<R> extends AbstractDistributeAggregatableNode<R>
    implements DistributeAggregatableNode<R> {

    public DefaultDistributeAggregatableNode() {
        this(new DistributeAggregateProcessorImpl<>());
    }

    public DefaultDistributeAggregatableNode(DistributeAggregateProcessorImpl<R> processor) {
        super(processor);
    }

}

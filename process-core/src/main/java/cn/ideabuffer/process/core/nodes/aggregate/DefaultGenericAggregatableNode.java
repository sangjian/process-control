package cn.ideabuffer.process.core.nodes.aggregate;

import cn.ideabuffer.process.core.aggregator.GenericAggregator;
import cn.ideabuffer.process.core.nodes.MergeableNode;
import cn.ideabuffer.process.core.processors.AggregateProcessor;
import cn.ideabuffer.process.core.processors.GenericAggregateProcessor;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/03/10
 */
public class DefaultGenericAggregatableNode<P, R>
    extends AbstractAggregatableNode<GenericAggregator<P, R>, MergeableNode<P>, R, GenericAggregateProcessor<P, R>> implements
    GenericAggregatableNode<P, R> {

    public DefaultGenericAggregatableNode() {
        this(new GenericAggregateProcessor<>());
    }

    public DefaultGenericAggregatableNode(GenericAggregateProcessor<P, R> processor) {
        super(processor);
    }
}

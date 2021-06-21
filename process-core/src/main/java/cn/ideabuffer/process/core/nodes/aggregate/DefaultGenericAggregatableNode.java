package cn.ideabuffer.process.core.nodes.aggregate;

import cn.ideabuffer.process.core.processors.AggregateProcessor;
import cn.ideabuffer.process.core.processors.impl.GenericAggregateProcessorImpl;

/**
 * @author sangjian.sj
 * @date 2020/03/10
 */
public class DefaultGenericAggregatableNode<I, O> extends AbstractAggregatableNode<I, O>
    implements GenericAggregatableNode<I, O> {

    public DefaultGenericAggregatableNode() {
        this(new GenericAggregateProcessorImpl<>());
    }

    public DefaultGenericAggregatableNode(AggregateProcessor<I, O> processor) {
        super(processor);
    }
}

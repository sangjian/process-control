package cn.ideabuffer.process.core.nodes.aggregate;

import cn.ideabuffer.process.core.processors.AggregateProcessor;
import cn.ideabuffer.process.core.processors.impl.UnitAggregateProcessorImpl;

/**
 * @author sangjian.sj
 * @date 2020/03/10
 */
public class DefaultUnitAggregatableNode<R> extends AbstractAggregatableNode<R, R>
    implements UnitAggregatableNode<R> {

    public DefaultUnitAggregatableNode() {
        this(new UnitAggregateProcessorImpl<>());
    }

    public DefaultUnitAggregatableNode(AggregateProcessor<R, R> processor) {
        super(processor);
    }

}

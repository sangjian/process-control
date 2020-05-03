package cn.ideabuffer.process.core.nodes.aggregate;

import cn.ideabuffer.process.core.aggregator.UnitAggregator;
import cn.ideabuffer.process.core.nodes.MergeableNode;
import cn.ideabuffer.process.core.processors.UnitAggregateProcessor;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/03/10
 */
public class DefaultUnitAggregatableNode<R> extends AbstractAggregatableNode<UnitAggregator<R>, MergeableNode<R>, R, UnitAggregateProcessor<R>>
    implements UnitAggregatableNode<R> {

    public DefaultUnitAggregatableNode() {
        this(new UnitAggregateProcessor<>());
    }

    public DefaultUnitAggregatableNode(UnitAggregateProcessor<R> processor) {
        super(processor);
    }

}

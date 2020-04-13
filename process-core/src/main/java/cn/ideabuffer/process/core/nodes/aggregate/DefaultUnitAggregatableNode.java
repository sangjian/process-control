package cn.ideabuffer.process.core.nodes.aggregate;

import cn.ideabuffer.process.core.handler.ExceptionHandler;
import cn.ideabuffer.process.core.nodes.MergeableNode;
import cn.ideabuffer.process.core.nodes.UnitAggregatableNode;
import cn.ideabuffer.process.core.rule.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.Executor;

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

    @Override
    public UnitAggregatableNode<R> exceptionHandler(ExceptionHandler handler) {
        super.exceptionHandler(handler);
        return this;
    }

    @Override
    public UnitAggregatableNode<R> parallel() {
        super.parallel();
        return this;
    }

    @Override
    public UnitAggregatableNode<R> parallel(Executor executor) {
        super.parallel(executor);
        return this;
    }

    @Override
    public UnitAggregatableNode<R> processOn(Rule rule) {
        super.processOn(rule);
        return this;
    }

    @Override
    public UnitAggregatableNode<R> aggregate(@NotNull MergeableNode<R>... nodes) {
        super.aggregate(nodes);
        return this;
    }

    @Override
    public UnitAggregatableNode<R> aggregator(@NotNull UnitAggregator<R> aggregator) {
        super.aggregator(aggregator);
        return this;
    }
}

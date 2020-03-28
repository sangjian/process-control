package cn.ideabuffer.process.nodes.aggregate;

import cn.ideabuffer.process.context.Context;
import cn.ideabuffer.process.handler.ExceptionHandler;
import cn.ideabuffer.process.nodes.MergeableNode;
import cn.ideabuffer.process.nodes.UnitAggregatableNode;
import cn.ideabuffer.process.nodes.transmitter.AbstractTransmittableNode;
import cn.ideabuffer.process.rule.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/03/10
 */
public class DefaultUnitAggregatableNode<R> extends AbstractTransmittableNode<R> implements UnitAggregatableNode<R> {

    private UnitAggregator<R> aggregator;

    private List<MergeableNode<R>> mergeableNodes;

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
        this.aggregator = aggregator;
        this.mergeableNodes = mergeableNodes == null ? new ArrayList<>() : mergeableNodes;
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
        this.mergeableNodes.addAll(Arrays.asList(nodes));
        return this;
    }

    @Override
    public UnitAggregatableNode<R> aggregator(@NotNull UnitAggregator<R> aggregator) {
        this.aggregator = aggregator;
        return this;
    }

    @Override
    public UnitAggregator<R> getAggregator() {
        return aggregator;
    }

    @Override
    public List<MergeableNode<R>> getMergeableNodes() {
        return mergeableNodes;
    }

    @Override
    protected R doInvoke(Context context) throws Exception {
        return aggregator.aggregate(context, getMergeableNodes());
    }
}

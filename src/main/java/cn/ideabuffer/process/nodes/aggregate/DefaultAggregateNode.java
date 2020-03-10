package cn.ideabuffer.process.nodes.aggregate;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.handler.ExceptionHandler;
import cn.ideabuffer.process.nodes.AggregatableNode;
import cn.ideabuffer.process.nodes.MergeableNode;
import cn.ideabuffer.process.nodes.merger.Merger;
import cn.ideabuffer.process.nodes.transmission.AbstractTransmittableNode;
import cn.ideabuffer.process.rule.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;

import static cn.ideabuffer.process.nodes.aggregate.Aggregators.SERIAL;

/**
 * @author sangjian.sj
 * @date 2020/03/10
 */
public class DefaultAggregateNode<R> extends AbstractTransmittableNode<R> implements AggregatableNode<R> {

    private Aggregator aggregator = SERIAL;

    private List<MergeableNode<R>> mergeableNodes;

    private Merger<R> merger;

    public DefaultAggregateNode() {
        this(null, null, null);
    }

    public DefaultAggregateNode(Aggregator aggregator) {
        this(aggregator, null, null);
    }

    public DefaultAggregateNode(List<MergeableNode<R>> mergeableNodes) {
        this(null, mergeableNodes, null);
    }

    public DefaultAggregateNode(Aggregator aggregator,
        List<MergeableNode<R>> mergeableNodes, Merger<R> merger) {
        if(aggregator != null) {
            this.aggregator = aggregator;
        }
        this.mergeableNodes = mergeableNodes == null ? new ArrayList<>() : mergeableNodes;
        this.merger = merger;
    }

    @Override
    public AggregatableNode<R> exceptionHandler(ExceptionHandler handler) {
        super.exceptionHandler(handler);
        return this;
    }

    @Override
    public AggregatableNode<R> parallel() {
        super.parallel();
        return this;
    }

    @Override
    public AggregatableNode<R> parallel(Executor executor) {
        super.parallel(executor);
        return this;
    }

    @Override
    public AggregatableNode<R> processOn(Rule rule) {
        super.processOn(rule);
        return this;
    }

    @Override
    public AggregatableNode<R> merge(@NotNull MergeableNode<R>... nodes) {
        this.mergeableNodes.addAll(Arrays.asList(nodes));
        return this;
    }

    @Override
    public AggregatableNode<R> by(@NotNull Merger<R> merger) {
        this.merger = merger;
        return this;
    }

    @Override
    public AggregatableNode<R> aggregator(@NotNull Aggregator aggregator) {
        this.aggregator = aggregator;
        return this;
    }

    @Override
    public Aggregator getAggregator() {
        return aggregator;
    }

    @Override
    public Merger<R> getMerger() {
        return merger;
    }

    @Override
    public List<MergeableNode<R>> getMergeableNodes() {
        return mergeableNodes;
    }

    @Override
    protected R doInvoke(Context context) throws Exception {
        return aggregator.aggregate(executor, merger, context, getMergeableNodes());
    }
}

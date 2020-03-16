package cn.ideabuffer.process.nodes.aggregate;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.handler.ExceptionHandler;
import cn.ideabuffer.process.nodes.AggregatableNode;
import cn.ideabuffer.process.nodes.MergeableNode;
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
public class DefaultAggregatableNode<R> extends AbstractTransmittableNode<R> implements AggregatableNode<R> {

    private Aggregator<R> aggregator;

    private List<MergeableNode<R>> mergeableNodes;

    public DefaultAggregatableNode() {
        this(null, null);
    }

    public DefaultAggregatableNode(Aggregator aggregator) {
        this(aggregator, null);
    }

    public DefaultAggregatableNode(List<MergeableNode<R>> mergeableNodes) {
        this(null, mergeableNodes);
    }

    public DefaultAggregatableNode(Aggregator aggregator, List<MergeableNode<R>> mergeableNodes) {
        if (aggregator != null) {
            this.aggregator = aggregator;
        }
        this.mergeableNodes = mergeableNodes == null ? new ArrayList<>() : mergeableNodes;
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
    public AggregatableNode<R> aggregator(@NotNull Aggregator<R> aggregator) {
        this.aggregator = aggregator;
        return this;
    }

    @Override
    public Aggregator<R> getAggregator() {
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

package cn.ideabuffer.process.nodes.aggregate;

import cn.ideabuffer.process.context.Context;
import cn.ideabuffer.process.handler.ExceptionHandler;
import cn.ideabuffer.process.nodes.DistributeAggregatableNode;
import cn.ideabuffer.process.nodes.DistributeMergeableNode;
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
public class DefaultDistributeAggregatableNode<R> extends AbstractTransmittableNode<R> implements
    DistributeAggregatableNode<R> {

    private DistributeAggregator<R> aggregator;

    private List<DistributeMergeableNode<?, R>> mergeableNodes;

    public DefaultDistributeAggregatableNode() {
        this(null, null);
    }

    public DefaultDistributeAggregatableNode(DistributeAggregator<R> aggregator) {
        this(aggregator, null);
    }

    public DefaultDistributeAggregatableNode(List<DistributeMergeableNode<?, R>> mergeableNodes) {
        this(null, mergeableNodes);
    }

    public DefaultDistributeAggregatableNode(DistributeAggregator<R> aggregator,
        List<DistributeMergeableNode<?, R>> mergeableNodes) {
        this.aggregator = aggregator;
        this.mergeableNodes = mergeableNodes == null ? new ArrayList<>() : mergeableNodes;
    }

    @Override
    public DistributeAggregatableNode<R> exceptionHandler(ExceptionHandler handler) {
        super.exceptionHandler(handler);
        return this;
    }

    @Override
    public DistributeAggregatableNode<R> parallel() {
        super.parallel();
        return this;
    }

    @Override
    public DistributeAggregatableNode<R> parallel(Executor executor) {
        super.parallel(executor);
        return this;
    }

    @Override
    public DistributeAggregatableNode<R> processOn(Rule rule) {
        super.processOn(rule);
        return this;
    }

    @Override
    public DistributeAggregatableNode<R> aggregate(@NotNull DistributeMergeableNode<?, R>... nodes) {
        this.mergeableNodes.addAll(Arrays.asList(nodes));
        return this;
    }

    @Override
    public DistributeAggregatableNode<R> aggregator(@NotNull DistributeAggregator<R> aggregator) {
        this.aggregator = aggregator;
        return this;
    }

    @Override
    public DistributeAggregator<R> getAggregator() {
        return aggregator;
    }

    @Override
    public List<DistributeMergeableNode<?, R>> getMergeableNodes() {
        return mergeableNodes;
    }

    @Override
    protected R doInvoke(Context context) throws Exception {
        return aggregator.aggregate(context, getMergeableNodes());
    }
}

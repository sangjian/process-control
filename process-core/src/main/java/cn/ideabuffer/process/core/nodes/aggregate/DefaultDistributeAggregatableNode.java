package cn.ideabuffer.process.core.nodes.aggregate;

import cn.ideabuffer.process.core.handler.ExceptionHandler;
import cn.ideabuffer.process.core.nodes.DistributeAggregatableNode;
import cn.ideabuffer.process.core.nodes.DistributeMergeableNode;
import cn.ideabuffer.process.core.rule.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/03/10
 */
public class DefaultDistributeAggregatableNode<R>
    extends AbstractAggregatableNode<DistributeAggregator<R>, DistributeMergeableNode<?, R>, R> implements
    DistributeAggregatableNode<R> {

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
        super(aggregator, mergeableNodes);
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
        super.aggregate(nodes);
        return this;
    }

    @Override
    public DistributeAggregatableNode<R> aggregator(@NotNull DistributeAggregator<R> aggregator) {
        super.aggregator(aggregator);
        return this;
    }
}

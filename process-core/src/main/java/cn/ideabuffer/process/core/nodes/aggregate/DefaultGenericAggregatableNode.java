package cn.ideabuffer.process.core.nodes.aggregate;

import cn.ideabuffer.process.core.handler.ExceptionHandler;
import cn.ideabuffer.process.core.nodes.GenericAggregatableNode;
import cn.ideabuffer.process.core.nodes.MergeableNode;
import cn.ideabuffer.process.core.rule.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/03/10
 */
public class DefaultGenericAggregatableNode<P, R>
    extends AbstractAggregatableNode<GenericAggregator<P, R>, MergeableNode<P>, R> implements
    GenericAggregatableNode<P, R> {

    public DefaultGenericAggregatableNode() {
        this(null, null);
    }

    public DefaultGenericAggregatableNode(GenericAggregator<P, R> aggregator) {
        this(aggregator, null);
    }

    public DefaultGenericAggregatableNode(List<MergeableNode<P>> mergeableNodes) {
        this(null, mergeableNodes);
    }

    public DefaultGenericAggregatableNode(GenericAggregator<P, R> aggregator, List<MergeableNode<P>> mergeableNodes) {
        super(aggregator, mergeableNodes);
    }

    @Override
    public GenericAggregatableNode<P, R> exceptionHandler(ExceptionHandler handler) {
        super.exceptionHandler(handler);
        return this;
    }

    @Override
    public GenericAggregatableNode<P, R> parallel() {
        super.parallel();
        return this;
    }

    @Override
    public GenericAggregatableNode<P, R> parallel(Executor executor) {
        super.parallel(executor);
        return this;
    }

    @Override
    public GenericAggregatableNode<P, R> processOn(Rule rule) {
        super.processOn(rule);
        return this;
    }

    @Override
    public GenericAggregatableNode<P, R> aggregate(@NotNull MergeableNode<P>... nodes) {
        super.aggregate(nodes);
        return this;
    }

    @Override
    public GenericAggregatableNode<P, R> aggregator(@NotNull GenericAggregator<P, R> aggregator) {
        super.aggregator(aggregator);
        return this;
    }
}

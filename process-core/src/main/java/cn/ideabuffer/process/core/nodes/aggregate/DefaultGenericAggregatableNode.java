package cn.ideabuffer.process.core.nodes.aggregate;

import cn.ideabuffer.process.core.Lifecycle;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.handler.ExceptionHandler;
import cn.ideabuffer.process.core.nodes.GenericAggregatableNode;
import cn.ideabuffer.process.core.nodes.MergeableNode;
import cn.ideabuffer.process.core.nodes.UnitAggregatableNode;
import cn.ideabuffer.process.core.nodes.transmitter.AbstractTransmittableNode;
import cn.ideabuffer.process.core.rule.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/03/10
 */
public class DefaultGenericAggregatableNode<P, R> extends AbstractTransmittableNode<R> implements
    GenericAggregatableNode<P, R> {

    private GenericAggregator<P, R> aggregator;

    private List<MergeableNode<P>> mergeableNodes;

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
        this.aggregator = aggregator;
        this.mergeableNodes = mergeableNodes == null ? new ArrayList<>() : mergeableNodes;
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
        this.mergeableNodes.addAll(Arrays.asList(nodes));
        return this;
    }

    @Override
    public GenericAggregatableNode<P, R> aggregator(@NotNull GenericAggregator<P, R> aggregator) {
        this.aggregator = aggregator;
        return this;
    }

    @Override
    public GenericAggregator<P, R> getAggregator() {
        return aggregator;
    }

    @Override
    public List<MergeableNode<P>> getMergeableNodes() {
        return mergeableNodes;
    }

    @Override
    protected R doInvoke(Context context) throws Exception {
        return aggregator.aggregate(context, getMergeableNodes());
    }

    @Override
    protected void onDestroy() {
        try {
            if (mergeableNodes != null) {
                mergeableNodes.forEach(Lifecycle::destroy);
            }
        } catch (Exception e) {
            logger.error("destroy encountered problem!", e);
            throw e;
        }
    }
}

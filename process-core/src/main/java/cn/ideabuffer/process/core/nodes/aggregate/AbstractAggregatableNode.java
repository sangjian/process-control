package cn.ideabuffer.process.core.nodes.aggregate;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.AggregatableNode;
import cn.ideabuffer.process.core.nodes.MergeableNode;
import cn.ideabuffer.process.core.nodes.transmitter.AbstractTransmittableNode;
import cn.ideabuffer.process.core.rule.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/04/13
 */
public abstract class AbstractAggregatableNode<A extends Aggregator<List<N>, R>, N extends MergeableNode<?>, R> extends AbstractTransmittableNode<R> implements
    AggregatableNode<A, N, R> {

    private A aggregator;

    private List<N> mergeableNodes;

    public AbstractAggregatableNode(List<N> mergeableNodes) {
        this(null, mergeableNodes);
    }

    public AbstractAggregatableNode(A aggregator, List<N> mergeableNodes) {
        this.aggregator = aggregator;
        this.mergeableNodes = mergeableNodes == null ? new ArrayList<>() : mergeableNodes;
    }

    public void setAggregator(A aggregator) {
        this.aggregator = aggregator;
    }

    public void setMergeableNodes(List<N> mergeableNodes) {
        this.mergeableNodes = mergeableNodes;
    }

    @Override
    public AggregatableNode<A, N, R> processOn(Rule rule) {
        super.processOn(rule);
        return this;
    }

    @Override
    public AggregatableNode<A, N, R> parallel() {
        super.parallel();
        return this;
    }

    @Override
    public AggregatableNode<A, N, R> parallel(Executor executor) {
        super.parallel(executor);
        return this;
    }

    @Override
    public AggregatableNode<A, N, R> aggregate(@NotNull N... nodes) {
        this.mergeableNodes.addAll(Arrays.asList(nodes));
        return this;
    }

    @Override
    public AggregatableNode<A, N, R> aggregator(@NotNull A aggregator) {
        this.aggregator = aggregator;
        return this;
    }

    @Override
    public A getAggregator() {
        return aggregator;
    }

    @Override
    public List<N> getMergeableNodes() {
        return mergeableNodes;
    }

    @Override
    protected R doInvoke(Context context) throws Exception {
        return getAggregator().aggregate(context, getMergeableNodes());
    }

    @Override
    protected void onDestroy() {
        List<N> mergeableNodes = getMergeableNodes();
        if (mergeableNodes == null || mergeableNodes.isEmpty()) {
            return;
        }
        mergeableNodes.forEach((node) -> {
            try {
                node.destroy();
            } catch (Exception e) {
                logger.error("destroy encountered problem, node:{}", node, e);
            }
        });
    }
}

package cn.ideabuffer.process.core.nodes.aggregate;

import cn.ideabuffer.process.core.aggregator.Aggregator;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.AggregatableNode;
import cn.ideabuffer.process.core.nodes.MergeableNode;
import cn.ideabuffer.process.core.nodes.transmitter.AbstractTransmittableNode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/04/13
 */
public abstract class AbstractAggregatableNode<A extends Aggregator<List<N>, R>, N extends MergeableNode<?>, R>
    extends AbstractTransmittableNode<R> implements
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

    @Override
    public A getAggregator() {
        return aggregator;
    }

    public void setAggregator(A aggregator) {
        this.aggregator = aggregator;
    }

    @Override
    public List<N> getMergeableNodes() {
        return mergeableNodes;
    }

    public void setMergeableNodes(List<N> mergeableNodes) {
        this.mergeableNodes = mergeableNodes;
    }

    @Override
    public void aggregate(@NotNull List<N> nodes) {
        this.mergeableNodes = nodes;
    }

    @Override
    public void aggregator(@NotNull A aggregator) {
        this.aggregator = aggregator;
    }

    @Override
    protected R doExecute(Context context) throws Exception {
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

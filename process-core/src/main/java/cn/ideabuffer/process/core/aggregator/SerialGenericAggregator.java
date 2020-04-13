package cn.ideabuffer.process.core.aggregator;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.MergeableNode;
import cn.ideabuffer.process.core.nodes.merger.Merger;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/03/09
 */
public class SerialGenericAggregator<I, O> implements GenericAggregator<I, O> {

    private Merger<I, O> merger;

    public SerialGenericAggregator(@NotNull Merger<I, O> merger) {
        this.merger = merger;
    }

    @Override
    public O aggregate(Context context, List<MergeableNode<I>> nodes)
        throws Exception {
        if (nodes == null || nodes.isEmpty()) {
            return null;
        }
        List<I> results = new LinkedList<>();
        for (MergeableNode<I> node : nodes) {
            results.add(node.invoke(context));
        }
        return merger.merge(results);
    }
}

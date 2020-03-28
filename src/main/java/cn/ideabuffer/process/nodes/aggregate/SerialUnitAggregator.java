package cn.ideabuffer.process.nodes.aggregate;

import cn.ideabuffer.process.context.Context;
import cn.ideabuffer.process.nodes.MergeableNode;
import cn.ideabuffer.process.nodes.merger.UnitMerger;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/03/09
 */
public class SerialUnitAggregator<R> implements UnitAggregator<R> {

    private UnitMerger<R> merger;

    public SerialUnitAggregator(@NotNull UnitMerger<R> merger) {
        this.merger = merger;
    }

    @Override
    public R aggregate(Context context, List<MergeableNode<R>> nodes)
        throws Exception {
        if (nodes == null || nodes.isEmpty()) {
            return null;
        }
        List<R> results = new LinkedList<>();
        for (MergeableNode<R> node : nodes) {
            results.add(node.invoke(context));
        }
        return merger.merge(results);
    }
}
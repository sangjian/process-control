package cn.ideabuffer.process.nodes.aggregate;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.nodes.MergeableNode;
import cn.ideabuffer.process.nodes.merger.Merger;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/03/09
 */
public class SerialAggregator<R> implements Aggregator<R> {

    private static final Logger logger = LoggerFactory.getLogger(SerialAggregator.class);

    private Merger<R> merger;

    public SerialAggregator(@NotNull Merger<R> merger) {
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

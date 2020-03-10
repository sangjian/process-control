package cn.ideabuffer.process.nodes.aggregate;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.nodes.MergeableNode;
import cn.ideabuffer.process.nodes.merger.Merger;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/03/09
 */
public class SerialAggregator implements Aggregator {
    @Override
    public <T> T aggregate(Executor executor, Merger<T> merger, Context context, List<MergeableNode<T>> nodes)
        throws Exception {
        if (nodes == null || nodes.isEmpty()) {
            return null;
        }
        List<T> results = new LinkedList<>();
        nodes.forEach(node -> {
            results.add(node.invoke(context));
        });
        return merger.merge(results);
    }
}

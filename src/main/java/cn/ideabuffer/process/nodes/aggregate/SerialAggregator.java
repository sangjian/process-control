package cn.ideabuffer.process.nodes.aggregate;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.nodes.MergeableNode;
import cn.ideabuffer.process.nodes.merger.Merger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/03/09
 */
public class SerialAggregator implements Aggregator {

    private static final Logger logger = LoggerFactory.getLogger(SerialAggregator.class);

    @Override
    public <T> T aggregate(Executor executor, Merger<T> merger, Context context, List<MergeableNode<T>> nodes)
        throws Exception {
        if (nodes == null || nodes.isEmpty()) {
            return null;
        }
        List<T> results = new LinkedList<>();
        for (MergeableNode<T> node : nodes) {
            results.add(node.invoke(context));
        }
        return merger.merge(results);
    }
}

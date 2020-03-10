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
        nodes.forEach(node -> {
            try {
                results.add(node.invoke(context));
            } catch (Exception e) {
                if(node.getExceptionHandler() != null) {
                    try {
                        node.getExceptionHandler().handle(e);
                    } catch (Exception ex) {
                        logger.error("handle exception error, node:{}", node, ex);
                    }
                } else {
                    throw new RuntimeException(e);
                }
            }
        });
        return merger.merge(results);
    }
}

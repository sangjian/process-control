package cn.ideabuffer.process.nodes.aggregate;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.nodes.MergeableNode;
import cn.ideabuffer.process.nodes.merger.Merger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Supplier;

/**
 * @author sangjian.sj
 * @date 2020/03/08
 */
public class ParallelAggregator implements Aggregator {

    private static final Logger logger = LoggerFactory.getLogger(ParallelAggregator.class);

    @Override
    public <T> T aggregate(Executor executor, Merger<T> merger, Context context, List<MergeableNode<T>> nodes)
        throws Exception {
        if (nodes == null || nodes.isEmpty()) {
            return null;
        }

        BlockingQueue<T> queue = new LinkedBlockingQueue<>(nodes.size());
        List<T> results = new LinkedList<>();
        CompletableFuture.allOf(nodes.stream().map(node -> {
            Supplier<T> supplier = new InvokeSupplier<>(node, context);
            CompletableFuture<T> future;
            if (executor == null) {
                future = CompletableFuture.supplyAsync(supplier);
            } else {
                future = CompletableFuture.supplyAsync(supplier, executor);
            }
            future.exceptionally(throwable -> {
                if (node.getExceptionHandler() != null) {
                    try {
                        node.getExceptionHandler().handle(throwable);
                    } catch (Exception e) {
                        // do nothing...
                    }
                } else {
                    logger.error("node invoke error, node:{}", node, throwable);
                }
                return null;
            }).thenAccept(queue::offer);
            return future;
        }).toArray(CompletableFuture[]::new)).join();
        queue.drainTo(results);

        return merger.merge(results);
    }

    class InvokeSupplier<V> implements Supplier<V> {

        private MergeableNode<V> node;

        private Context context;

        InvokeSupplier(MergeableNode<V> node, Context context) {
            this.node = node;
            this.context = context;
        }

        @Override
        public V get() {
            try {
                return node.invoke(context);
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
            return null;
        }
    }
}

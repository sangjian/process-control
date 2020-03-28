package cn.ideabuffer.process.nodes.aggregate;

import cn.ideabuffer.process.context.Context;
import cn.ideabuffer.process.nodes.MergeableNode;
import cn.ideabuffer.process.nodes.merger.UnitMerger;
import org.jetbrains.annotations.NotNull;
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
public class ParallelUnitAggregator<R> implements UnitAggregator<R> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Executor executor;
    private UnitMerger<R> merger;

    public ParallelUnitAggregator(@NotNull Executor executor, @NotNull UnitMerger<R> merger) {
        this.executor = executor;
        this.merger = merger;
    }

    @Override
    public R aggregate(Context context, List<MergeableNode<R>> nodes) throws Exception {
        if (nodes == null || nodes.isEmpty()) {
            return null;
        }

        BlockingQueue<R> queue = new LinkedBlockingQueue<>(nodes.size());
        List<R> results = new LinkedList<>();
        CompletableFuture.allOf(nodes.stream().map(node -> {
            Supplier<R> supplier = new InvokeSupplier<>(node, context);
            CompletableFuture<R> future;
            if (executor == null) {
                future = CompletableFuture.supplyAsync(supplier);
            } else {
                future = CompletableFuture.supplyAsync(supplier, executor);
            }
            future.thenAccept(r -> {
                if (r != null) {
                    queue.offer(r);
                }
            });
            return future;
        }).toArray(CompletableFuture[]::new)).join();
        queue.drainTo(results);

        return merger.merge(results);
    }

    private class InvokeSupplier<V> implements Supplier<V> {

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
                logger.error("InvokeSupplier invoke error, node:{}", node, e);
                throw new RuntimeException(e);
            }
        }
    }
}

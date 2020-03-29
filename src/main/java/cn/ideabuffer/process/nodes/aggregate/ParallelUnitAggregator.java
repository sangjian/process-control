package cn.ideabuffer.process.nodes.aggregate;

import cn.ideabuffer.process.context.Context;
import cn.ideabuffer.process.nodes.MergeableNode;
import cn.ideabuffer.process.nodes.merger.UnitMerger;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
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

        // 用于保存节点执行结果
        BlockingQueue<R> resultQueue = new LinkedBlockingQueue<>(nodes.size());
        // 保存节点执行结果，在所有节点执行完毕后，由resultQueue获取
        List<R> results = new LinkedList<>();
        // 节点如果有设置了超时时间，则获取最大的超时时间
        long maxTimeout = Aggregators.getMaxTimeout(nodes);
        // 获取有超时控制的节点
        CompletableFuture[] timeouts = nodes.stream().filter(n -> n.getTimeout() > 0).map(node -> {
            CompletableFuture<R> f = getFuture(context, node);
            f.thenAccept(r -> {
                if (r != null) {
                    resultQueue.offer(r);
                }
            });
            return f;
        }).toArray(CompletableFuture[]::new);
        // 获取没有超时控制的节点
        CompletableFuture[] normals = nodes.stream().filter(n -> n.getTimeout() <= 0).map(node -> {
            CompletableFuture<R> f = getFuture(context, node);
            f.thenAccept(r -> {
                if (r != null) {
                    resultQueue.offer(r);
                }
            });
            return f;
        }).toArray(CompletableFuture[]::new);

        CompletableFuture<Void> allTimeouts = CompletableFuture.allOf(timeouts);
        CompletableFuture<Void> allNormals = CompletableFuture.allOf(normals);
        try {
            // 如果有超时控制，则等待最大的超时时间
            if (maxTimeout > 0) {
                allTimeouts.get(maxTimeout, TimeUnit.MILLISECONDS);
            }
        } catch (TimeoutException e) {
            logger.error("timeout!", e);
        } finally {
            // 确保没有超时控制的节点执行结束
            allNormals.join();
            resultQueue.drainTo(results);
        }

        return merger.merge(results);
    }

    private CompletableFuture<R> getFuture(Context context, MergeableNode<R> node) {
        Supplier<R> supplier = new InvokeSupplier<>(node, context);
        CompletableFuture<R> future;
        if (executor == null) {
            future = CompletableFuture.supplyAsync(supplier);
        } else {
            future = CompletableFuture.supplyAsync(supplier, executor);
        }
        return future;
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

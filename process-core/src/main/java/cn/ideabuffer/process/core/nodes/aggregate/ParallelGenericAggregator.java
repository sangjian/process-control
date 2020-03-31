package cn.ideabuffer.process.core.nodes.aggregate;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.merger.Merger;
import cn.ideabuffer.process.core.nodes.MergeableNode;
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
public class ParallelGenericAggregator<I, O> implements GenericAggregator<I, O> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Executor executor;
    private Merger<I, O> merger;

    public ParallelGenericAggregator(@NotNull Executor executor, @NotNull Merger<I, O> merger) {
        this.executor = executor;
        this.merger = merger;
    }

    @Override
    public O aggregate(Context context, List<MergeableNode<I>> nodes) throws Exception {
        if (nodes == null || nodes.isEmpty()) {
            return null;
        }

        // 用于保存节点执行结果
        BlockingQueue<I> resultQueue = new LinkedBlockingQueue<>(nodes.size());
        List<CompletableFuture<I>> timeouts = new LinkedList<>();
        List<CompletableFuture<I>> normals = new LinkedList<>();
        // 保存节点执行结果，在所有节点执行完毕后，由resultQueue获取
        List<I> results = new LinkedList<>();
        // 节点如果有设置了超时时间，则获取最大的超时时间
        long maxTimeout = Aggregators.getMaxTimeout(nodes);

        nodes.forEach(node -> {
            CompletableFuture<I> f = getFuture(context, node);
            f.thenAccept(r -> {
                if (r != null) {
                    resultQueue.offer(r);
                }
            });
            if (node.getTimeout() > 0) {
                timeouts.add(f);
            } else {
                normals.add(f);
            }
        });

        CompletableFuture<Void> allTimeouts = CompletableFuture.allOf(timeouts.toArray(new CompletableFuture[0]));
        CompletableFuture<Void> allNormals = CompletableFuture.allOf(normals.toArray(new CompletableFuture[0]));
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

    private CompletableFuture<I> getFuture(Context context, MergeableNode<I> node) {
        Supplier<I> supplier = new InvokeSupplier<>(node, context);
        CompletableFuture<I> future;
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

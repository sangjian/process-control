package cn.ideabuffer.process.nodes.aggregate;

import cn.ideabuffer.process.context.Context;
import cn.ideabuffer.process.nodes.DistributeMergeableNode;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * @author sangjian.sj
 * @date 2020/03/08
 */
public class ParallelDistributeAggregator<R> implements DistributeAggregator<R> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Executor executor;
    private Class<R> resultClass;

    public ParallelDistributeAggregator(@NotNull Executor executor, @NotNull Class<R> resultClass) {
        this.executor = executor;
        this.resultClass = resultClass;
    }

    @Override
    public R aggregate(Context context, List<DistributeMergeableNode<?, R>> nodes) throws Exception {
        if (nodes == null || nodes.isEmpty()) {
            return null;
        }
        R result;
        try {
            result = resultClass.newInstance();
        } catch (Exception e) {
            logger.error("aggregate error, resultClass:{}, nodes:{}", resultClass, nodes, e);
            throw new RuntimeException(e);
        }
        long maxTimeout = Aggregators.getMaxTimeout(nodes);
        BlockingQueue<MergerNode<?, R>> mergerNodes = new LinkedBlockingQueue<>(nodes.size());
        // 获取有超时控制的节点
        CompletableFuture[] timeouts = nodes.stream().filter(n -> n.getTimeout() > 0).map(
            node -> {
                CompletableFuture<?> f = getFuture(context, node);
                // 因为结果对象只有一个，为了避免并发可能引起的问题，这里不希望并行地去merge；
                // 将返回结果封装成对象后放入队列，所有节点执行完毕后再进行统一merge
                //noinspection unchecked

                f.thenAccept(v -> {
                    //noinspection unchecked
                    MergerNode<?, R> m = new MergerNode(node, v, result);
                    mergerNodes.offer(m);
                });
                return f;
            }).toArray(CompletableFuture[]::new);
        // 获取没有超时控制的节点
        CompletableFuture[] normals = nodes.stream().filter(n -> n.getTimeout() <= 0).map(
            node -> {
                CompletableFuture<?> f = getFuture(context, node);
                // 因为结果对象只有一个，为了避免并发可能引起的问题，这里不希望并行地去merge；
                // 将返回结果封装成对象后放入队列，所有节点执行完毕后再进行统一merge
                f.thenAccept(v -> {
                    //noinspection unchecked
                    MergerNode<?, R> m = new MergerNode(node, v, result);
                    mergerNodes.offer(m);
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
            // 统一merge结果
            mergerNodes.forEach(MergerNode::merge);
        }

        return result;
    }

    private CompletableFuture<?> getFuture(Context context, DistributeMergeableNode<?, R> node) {
        Supplier<?> supplier = new InvokeSupplier<>(node, context);
        CompletableFuture<?> future;
        if (executor == null) {
            future = CompletableFuture.supplyAsync(supplier);
        } else {
            future = CompletableFuture.supplyAsync(supplier, executor);
        }
        return future;
    }

    private class MergerNode<V, T> {
        private DistributeMergeableNode<V, T> node;
        private V value;
        private T result;

        MergerNode(DistributeMergeableNode<V, T> node, V value, T result) {
            this.node = node;
            this.value = value;
            this.result = result;
        }

        public void merge() {
            node.merge(value, result);
        }
    }

    private class InvokeSupplier<V> implements Supplier<V> {

        private DistributeMergeableNode<V, ?> node;

        private Context context;

        InvokeSupplier(DistributeMergeableNode<V, ?> node, Context context) {
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

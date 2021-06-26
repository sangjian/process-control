package cn.ideabuffer.process.core.aggregator;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.DistributeMergeableNode;
import cn.ideabuffer.process.core.util.AggregateUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 并行分布式聚合器
 *
 * @param <R> 聚合结果类型
 * @author sangjian.sj
 * @date 2020/03/08
 * @see DistributeAggregator
 */
public class ParallelDistributeAggregator<R> extends AbstractAggregator implements DistributeAggregator<R> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Executor executor;

    /**
     * 返回结果类型，该类型必须有无参构造器
     */
    private Class<R> resultClass;

    /**
     * 聚合器执行超时时间
     */
    private long timeout;

    public ParallelDistributeAggregator(@NotNull Executor executor, @NotNull Class<R> resultClass) {
        this(executor, resultClass, 0);
    }

    public ParallelDistributeAggregator(@NotNull Executor executor, @NotNull Class<R> resultClass, long timeout) {
        this(executor, resultClass, timeout, TimeUnit.MILLISECONDS);
    }

    public ParallelDistributeAggregator(@NotNull Executor executor, @NotNull Class<R> resultClass, long timeout,
        @NotNull TimeUnit unit) {
        if (timeout < 0) {
            throw new IllegalArgumentException("timeout must >= 0");
        }
        this.executor = executor;
        this.resultClass = resultClass;
        this.timeout = unit.toMillis(timeout);
    }

    @NotNull
    @Override
    public R aggregate(@NotNull Context context, List<DistributeMergeableNode<?, R>> nodes) throws Exception {
        R result;
        // 创建结果对象
        try {
            result = resultClass.newInstance();
        } catch (Exception e) {
            logger.error("aggregate error, resultClass:{}, nodes:{}", resultClass, nodes, e);
            throw e;
        }
        if (nodes == null || nodes.isEmpty()) {
            return result;
        }
        BlockingQueue<MergerNode<?, R>> mergerNodes = new LinkedBlockingQueue<>(nodes.size());
        List<CompletableFuture<?>> futureList = new LinkedList<>();
        nodes.forEach(node -> {
            CompletableFuture<?> f = getFuture(context, node, mergerNodes, result);
            futureList.add(f);
        });

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0]));

        try {
            if (timeout > 0) {
                allFutures.get(timeout, TimeUnit.MILLISECONDS);
            } else {
                allFutures.get();
            }
        } catch (TimeoutException e) {
            logger.error("aggregator timeout! timeout:{}", timeout, e);
        } finally {
            // 统一merge结果
            mergerNodes.forEach(MergerNode::merge);
        }

        return result;
    }

    private CompletableFuture<?> getFuture(Context context, DistributeMergeableNode<?, R> node,
        BlockingQueue<MergerNode<?, R>> mergerNodes, R result) {
        CompletableFuture<?> future = CompletableFuture.supplyAsync(() -> AggregateUtils.process(context, node),
            executor)
            .thenAccept(v -> {
                // 因为结果对象只有一个，为了避免并发可能引起的问题，这里不希望并行地去merge；
                // 将返回结果封装成对象后放入队列，所有节点执行完毕后再进行统一merge
                //noinspection unchecked
                MergerNode<?, R> m = new MergerNode(node, v, result);
                mergerNodes.offer(m);
            }).exceptionally(t -> {
                logger.error("process error! node:{}", node, t);
                return null;
            });
        if (node.getTimeout() <= 0) {
            return future;
        }
        return AggregateUtils.within(future, node.getTimeout(), TimeUnit.MILLISECONDS);
    }

    private class MergerNode<V, R> {
        private DistributeMergeableNode<V, R> node;
        private V value;
        private R result;

        MergerNode(DistributeMergeableNode<V, R> node, V value, R result) {
            this.node = node;
            this.value = value;
            this.result = result;
        }

        public void merge() {
            if (node.getProcessor() != null) {
                node.getProcessor().merge(value, result);
            }
        }
    }

    @Override
    public void destroy() {
        if (executor instanceof ExecutorService && !((ExecutorService)executor).isShutdown()) {
            ((ExecutorService)executor).shutdown();
        }
    }
}

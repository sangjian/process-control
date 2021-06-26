package cn.ideabuffer.process.core.aggregators;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.GenericMergeableNode;
import cn.ideabuffer.process.core.nodes.merger.Merger;
import cn.ideabuffer.process.core.utils.AggregateUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 并行通用聚合器
 *
 * @param <I> 输入类型
 * @param <O> 输出类型
 * @author sangjian.sj
 * @date 2020/03/08
 * @see GenericAggregator
 */
public class ParallelGenericAggregator<I, O> extends AbstractAggregator implements GenericAggregator<I, O> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Executor executor;

    /**
     * 结果合并器
     */
    private Merger<I, O> merger;

    /**
     * 聚合器执行超时时间
     */
    private long timeout;

    public ParallelGenericAggregator(@NotNull Executor executor, @NotNull Merger<I, O> merger) {
        this(executor, merger, 0L);
    }

    public ParallelGenericAggregator(@NotNull Executor executor, @NotNull Merger<I, O> merger, long timeout) {
        this(executor, merger, timeout, TimeUnit.MILLISECONDS);
    }

    public ParallelGenericAggregator(@NotNull Executor executor, @NotNull Merger<I, O> merger, long timeout,
        @NotNull TimeUnit unit) {
        if (timeout < 0) {
            throw new IllegalArgumentException("timeout must >= 0");
        }
        this.executor = executor;
        this.merger = merger;
        this.timeout = unit.toMillis(timeout);
    }

    @Nullable
    @Override
    public O aggregate(@NotNull Context context, List<GenericMergeableNode<I>> nodes) throws Exception {
        if (nodes == null || nodes.isEmpty()) {
            return null;
        }

        // 用于保存节点执行结果
        BlockingQueue<I> resultQueue = new LinkedBlockingQueue<>(nodes.size());
        List<CompletableFuture<?>> futureList = new LinkedList<>();
        // 保存节点执行结果，在所有节点执行完毕后，由resultQueue获取
        List<I> results = new LinkedList<>();

        nodes.forEach(node -> {
            CompletableFuture<?> f = getFuture(context, node, resultQueue);
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
            logger.error("aggregate timeout in {} milliseconds", timeout, e);
        } finally {
            resultQueue.drainTo(results);
        }

        return merger.merge(results);
    }

    private CompletableFuture<?> getFuture(Context context, GenericMergeableNode<I> node,
        BlockingQueue<I> resultQueue) {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> AggregateUtils.process(context, node),
            executor)
            .thenAccept(r -> {
                if (r != null) {
                    resultQueue.offer(r);
                }
            }).exceptionally(t -> {
                logger.error("process error! node:{}", node, t);
                return null;
            });
        if (node.getTimeout() <= 0) {
            return future;
        }
        return AggregateUtils.within(future, node.getTimeout(), TimeUnit.MILLISECONDS);
    }

    @Override
    public void destroy() {
        if (executor instanceof ExecutorService && !((ExecutorService)executor).isShutdown()) {
            ((ExecutorService)executor).shutdown();
        }
    }
}

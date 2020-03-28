package cn.ideabuffer.process.nodes.aggregate;

import cn.ideabuffer.process.context.Context;
import cn.ideabuffer.process.nodes.DistributeMergeableNode;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

/**
 * @author sangjian.sj
 * @date 2020/03/08
 */
public class ParallelDistributeAggregator<R> implements DistributeAggregator<R> {

    private static final Logger logger = LoggerFactory.getLogger(ParallelDistributeAggregator.class);

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
        CompletableFuture.allOf(nodes.stream().map(node -> {
            Supplier<R> supplier = new InvokeSupplier<>(node, context, result);
            CompletableFuture<?> future;
            if (executor == null) {
                future = CompletableFuture.supplyAsync(supplier);
            } else {
                future = CompletableFuture.supplyAsync(supplier, executor);
            }

            return future;
        }).toArray(CompletableFuture[]::new)).join();

        return result;
    }

    private class InvokeSupplier<V, T> implements Supplier<T> {

        private DistributeMergeableNode<V, T> node;

        private Context context;

        private T result;

        InvokeSupplier(DistributeMergeableNode<V, T> node, Context context, T result) {
            this.node = node;
            this.context = context;
            this.result = result;
        }

        @Override
        public T get() {
            try {
                V v = node.invoke(context);
                return node.merge(v, result);
            } catch (Exception e) {
                logger.error("InvokeSupplier invoke error, node:{}", node, e);
                throw new RuntimeException(e);
            }
        }
    }
}

package cn.ideabuffer.process.core.utils;

import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.exception.ProcessException;
import cn.ideabuffer.process.core.nodes.DistributeMergeableNode;
import cn.ideabuffer.process.core.nodes.GenericMergeableNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author sangjian.sj
 * @date 2020/05/16
 */
public class AggregateUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(AggregateUtils.class);

    private static final ScheduledThreadPoolExecutor SCHEDULER = new ScheduledThreadPoolExecutor(
        Runtime.getRuntime().availableProcessors(),
        new TimeoutThreadFactory());

    private AggregateUtils() {
        throw new IllegalStateException("Utility class");
    }

    @Nullable
    public static <V> V process(@NotNull Context context, @NotNull GenericMergeableNode<V> node) {
        return process(node.getProcessor(), context);
    }

    @Nullable
    public static <I, V> I process(@NotNull Context context, @NotNull DistributeMergeableNode<I, V> node) {
        return process(node.getProcessor(), context);
    }

    @Nullable
    public static <V> V process(Processor<V> processor, Context context) {
        if (processor == null) {
            return null;
        }
        try {
            return processor.process(context);
        } catch (Exception e) {
            LOGGER.error("invoke process error", e);
            throw new ProcessException(e);
        }
    }

    @NotNull
    public static CompletableFuture<Void> within(@NotNull CompletableFuture<?> future, long timeout,
        @NotNull TimeUnit unit) {
        long readTimeout = unit.toMillis(timeout);
        return future.acceptEither(failIn(readTimeout, unit), t -> {
        }).exceptionally(t -> {
            Throwable exp;
            if ((exp = t) instanceof TimeoutException || (exp = t.getCause()) instanceof TimeoutException) {
                LOGGER.error("timeout in {} millis", readTimeout, exp);
                future.completeExceptionally(exp);
                return null;
            }
            LOGGER.error("within exceptionally", t);
            return null;
        });
    }

    @NotNull
    public static <T> CompletableFuture<T> failIn(long timeout, TimeUnit unit) {
        CompletableFuture<T> future = new CompletableFuture<>();
        SCHEDULER.schedule(() -> future.completeExceptionally(
            new TimeoutException(String.format("timeout after %d millis", unit.toMillis(timeout)))), timeout, unit);
        return future;
    }

    static class TimeoutThreadFactory implements ThreadFactory {
        private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        TimeoutThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = "aggregate-timeout-pool-" + POOL_NUMBER.getAndIncrement() + "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(),
                0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }

}

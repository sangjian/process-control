package cn.ideabuffer.process.core.aggregator;

import cn.ideabuffer.process.core.nodes.merger.Merger;
import cn.ideabuffer.process.core.nodes.merger.UnitMerger;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * @author sangjian.sj
 * @date 2020/03/09
 */
public class Aggregators {

    private Aggregators() {
        throw new IllegalStateException("Utility class");
    }

    public static <I, O> GenericAggregator<I, O> newParallelGenericAggregator(@NotNull Executor executor,
        @NotNull Merger<I, O> merger) {
        return newParallelGenericAggregator(executor, merger, 0);
    }

    public static <I, O> GenericAggregator<I, O> newParallelGenericAggregator(@NotNull Executor executor,
        @NotNull Merger<I, O> merger, long timeout) {
        return newParallelGenericAggregator(executor, merger, timeout, TimeUnit.MILLISECONDS);
    }

    public static <I, O> GenericAggregator<I, O> newParallelGenericAggregator(@NotNull Executor executor,
        @NotNull Merger<I, O> merger, long timeout, @NotNull TimeUnit unit) {
        return new ParallelGenericAggregator<>(executor, merger, timeout, unit);
    }

    public static <R> UnitAggregator<R> newParallelUnitAggregator(@NotNull Executor executor,
        @NotNull UnitMerger<R> merger) {
        return new ParallelUnitAggregator<>(executor, merger);
    }

    public static <I, O> GenericAggregator<I, O> newSerialGenericAggregator(@NotNull Merger<I, O> merger) {
        return new SerialGenericAggregator<>(merger);
    }

    public static <R> UnitAggregator<R> newSerialUnitAggregator(@NotNull UnitMerger<R> merger) {
        return new SerialUnitAggregator<>(merger);
    }

    public static <O> DistributeAggregator<O> newParallelDistributeAggregator(
        @NotNull Executor executor,
        @NotNull Class<O> resultClass) {
        return newParallelDistributeAggregator(executor, resultClass, 0);
    }

    public static <O> DistributeAggregator<O> newParallelDistributeAggregator(
        @NotNull Executor executor,
        @NotNull Class<O> resultClass, long timeout) {
        return newParallelDistributeAggregator(executor, resultClass, timeout, TimeUnit.MILLISECONDS);
    }

    public static <O> DistributeAggregator<O> newParallelDistributeAggregator(
        @NotNull Executor executor,
        @NotNull Class<O> resultClass, long timeout, @NotNull TimeUnit unit) {
        return new ParallelDistributeAggregator<>(executor, resultClass, timeout, unit);
    }

}

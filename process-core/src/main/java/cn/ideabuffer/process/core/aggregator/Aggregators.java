package cn.ideabuffer.process.core.aggregator;

import cn.ideabuffer.process.core.Mergeable;
import cn.ideabuffer.process.core.nodes.DistributeMergeableNode;
import cn.ideabuffer.process.core.nodes.merger.Merger;
import cn.ideabuffer.process.core.nodes.merger.UnitMerger;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.Executor;

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
        return new ParallelGenericAggregator<>(executor, merger);
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
        return new ParallelDistributeAggregator<>(executor, resultClass);
    }

    public static long getMaxTimeout(@NotNull List<? extends Mergeable> nodes) {
        return nodes.stream().mapToLong(Mergeable::getTimeout).max().orElse(0);
    }

}

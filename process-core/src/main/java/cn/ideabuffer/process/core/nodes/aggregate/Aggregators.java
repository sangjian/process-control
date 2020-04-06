package cn.ideabuffer.process.core.nodes.aggregate;

import cn.ideabuffer.process.core.nodes.MergeableNode;
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

    public static <R> DistributeAggregator<R> newParallelDistributeAggregator(@NotNull Executor executor,
        @NotNull Class<R> resultClass) {
        return new ParallelDistributeAggregator<>(executor, resultClass);
    }

    public static long getMaxTimeout(@NotNull List<? extends MergeableNode<?>> nodes) {
        return nodes.stream().mapToLong(MergeableNode::getTimeout).max().orElse(0);
    }

}

package cn.ideabuffer.process.nodes.aggregate;

import cn.ideabuffer.process.nodes.merger.UnitMerger;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/03/09
 */
public class Aggregators {

    private Aggregators() {
        throw new IllegalStateException("Utility class");
    }

    public static <R> UnitAggregator<R> newParallelUnitAggregator(@NotNull Executor executor,
        @NotNull UnitMerger<R> merger) {
        return new ParallelUnitAggregator<>(executor, merger);
    }

    public static <R> UnitAggregator<R> newSerialUnitAggregator(@NotNull UnitMerger<R> merger) {
        return new SerialUnitAggregator<>(merger);
    }

    public static <R> DistributeAggregator<R> newParallelDistributeAggregator(@NotNull Executor executor,
        @NotNull Class<R> resultClass) {
        return new ParallelDistributeAggregator<>(executor, resultClass);
    }

}

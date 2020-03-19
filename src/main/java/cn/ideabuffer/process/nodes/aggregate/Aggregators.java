package cn.ideabuffer.process.nodes.aggregate;

import cn.ideabuffer.process.nodes.merger.Merger;
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

    public static <R> Aggregator<R> newParallelAggregator(@NotNull Executor executor, @NotNull Merger<R> merger) {
        return new ParallelAggregator<>(executor, merger);
    }

    public static <R> Aggregator<R> newSerialAggregator(@NotNull Merger<R> merger) {
        return new SerialAggregator<>(merger);
    }

}

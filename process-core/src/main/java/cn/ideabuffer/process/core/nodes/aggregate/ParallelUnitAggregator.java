package cn.ideabuffer.process.core.nodes.aggregate;

import cn.ideabuffer.process.core.nodes.merger.UnitMerger;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/03/08
 */
public class ParallelUnitAggregator<R> extends ParallelGenericAggregator<R, R> implements UnitAggregator<R> {

    public ParallelUnitAggregator(@NotNull Executor executor,
        @NotNull UnitMerger<R> merger) {
        super(executor, merger);
    }
}

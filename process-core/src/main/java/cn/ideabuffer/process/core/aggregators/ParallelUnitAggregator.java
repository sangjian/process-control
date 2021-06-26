package cn.ideabuffer.process.core.aggregators;

import cn.ideabuffer.process.core.nodes.merger.UnitMerger;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;

/**
 * 并行单元化聚合器
 *
 * @param <R> 输入类型/输出类型，输入与输出类型一致
 * @author sangjian.sj
 * @date 2020/03/08
 * @see UnitAggregator
 */
public class ParallelUnitAggregator<R> extends ParallelGenericAggregator<R, R> implements UnitAggregator<R> {

    public ParallelUnitAggregator(@NotNull Executor executor,
        @NotNull UnitMerger<R> merger) {
        super(executor, merger);
    }
}

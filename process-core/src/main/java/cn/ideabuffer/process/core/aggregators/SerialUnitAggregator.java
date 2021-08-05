package cn.ideabuffer.process.core.aggregators;

import cn.ideabuffer.process.core.nodes.merger.UnitMerger;
import org.jetbrains.annotations.NotNull;

/**
 * 串行单元化聚合器
 *
 * @param <R> 输入类型/输出类型，输入与输出类型一致
 * @author sangjian.sj
 * @date 2020/03/09
 * @see UnitAggregator
 */
public class SerialUnitAggregator<R> extends SerialGenericAggregator<R, R> implements UnitAggregator<R> {

    public SerialUnitAggregator(@NotNull UnitMerger<R> merger) {
        super(merger);
    }

}

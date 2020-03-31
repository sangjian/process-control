package cn.ideabuffer.process.core.nodes.aggregate;

import cn.ideabuffer.process.core.nodes.merger.UnitMerger;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/03/09
 */
public class SerialUnitAggregator<R> extends SerialGenericAggregator<R, R> implements UnitAggregator<R> {

    public SerialUnitAggregator(@NotNull UnitMerger<R> merger) {
        super(merger);
    }

}

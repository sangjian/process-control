package cn.ideabuffer.process.core.test.processors.aggregate;

import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.context.Context;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/03/11
 */
public class DoubleMergeNodeProcessor2 implements Processor<Double> {

    @Override
    public Double process(@NotNull Context context) throws Exception {
        return 13d;
    }
}

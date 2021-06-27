package cn.ideabuffer.process.core.test.processors.aggregate;

import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.context.Context;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/03/11
 */
public class IntArrayMergeNodeProcessor2 implements Processor<int[]> {

    @Override
    public int[] process(@NotNull Context context) throws Exception {
        return new int[] {3, 5, 8};
    }
}

package cn.ideabuffer.process.core.test.nodes.aggregate;

import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.AbstractMergeableNode;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/03/11
 */
public class IntMergeNodeProcessor2 implements Processor<Integer> {

    @Override
    public Integer process(@NotNull Context context) throws Exception {
        return 13;
    }
}

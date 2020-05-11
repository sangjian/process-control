package cn.ideabuffer.process.core.test.nodes.trycatch;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.core.processors.StatusProcessor;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/02/28
 */
public class TryNodeProcessor2 implements StatusProcessor {

    @NotNull
    @Override
    public ProcessStatus process(@NotNull Context context) throws Exception {
        System.out.println("in TryNodeProcessor2");

        throw new NullPointerException();
    }
}

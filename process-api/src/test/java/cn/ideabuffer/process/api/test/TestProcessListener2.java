package cn.ideabuffer.process.api.test;

import cn.ideabuffer.process.core.ProcessListener;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/06/25
 */
public class TestProcessListener2 implements ProcessListener<ProcessStatus> {
    @Override
    public void onComplete(@NotNull Context context, ProcessStatus result) {

    }

    @Override
    public void onFailure(@NotNull Context context, Throwable t) {

    }
}

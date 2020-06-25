package cn.ideabuffer.process.api.test;

import cn.ideabuffer.process.annotation.model.ProcessModel;
import cn.ideabuffer.process.core.ProcessListener;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/06/25
 */
@ProcessModel(id = "TestProcessListener1", description = "测试监听器1")
public class TestProcessListener1 implements ProcessListener<ProcessStatus> {
    @Override
    public void onComplete(@NotNull Context context, ProcessStatus result) {

    }

    @Override
    public void onFailure(@NotNull Context context, Throwable t) {

    }
}

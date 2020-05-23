package cn.ideabuffer.process.core.test.nodes;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.processors.ResultProcessor;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/05/22
 */
public class TestBaseNodeProcessor implements ResultProcessor<String> {
    @Override
    public String process(@NotNull Context context, @NotNull ProcessStatus status) {
        return "TestBaseNodeProcessor";
    }
}

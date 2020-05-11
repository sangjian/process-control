package cn.ideabuffer.process.core.processors;

import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author sangjian.sj
 * @date 2020/05/03
 */
public interface StatusProcessor extends Processor<ProcessStatus> {

    @NotNull
    @Override
    ProcessStatus process(@NotNull Context context) throws Exception;
}

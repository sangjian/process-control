package cn.ideabuffer.process.core.processors.wrapper;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author sangjian.sj
 * @date 2021/06/18
 */
public interface StatusWrapperHandler extends WrapperHandler<ProcessStatus> {

    @NotNull
    @Override
    ProcessStatus afterReturning(@NotNull Context context, @NotNull ProcessStatus status);

}
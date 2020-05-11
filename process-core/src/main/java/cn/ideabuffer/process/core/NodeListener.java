package cn.ideabuffer.process.core;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author sangjian.sj
 * @date 2020/04/29
 */
public interface NodeListener<R> {

    ProcessStatus onComplete(@NotNull Context context, @Nullable R result);

    ProcessStatus onFailure(@NotNull Context context, @NotNull Throwable t);

}

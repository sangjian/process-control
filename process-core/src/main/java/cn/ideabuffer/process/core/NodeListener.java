package cn.ideabuffer.process.core;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/04/29
 */
public interface NodeListener<R> {

    ProcessStatus onComplete(@NotNull Context context, R result);

    ProcessStatus onFailure(@NotNull Context context, Throwable t);

}

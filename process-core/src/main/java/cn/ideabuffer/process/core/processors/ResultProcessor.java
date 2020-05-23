package cn.ideabuffer.process.core.processors;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/05/22
 */
public interface ResultProcessor<R> {

    R process(@NotNull Context context, @NotNull ProcessStatus status);

}

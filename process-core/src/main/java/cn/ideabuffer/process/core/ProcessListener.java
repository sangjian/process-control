package cn.ideabuffer.process.core;

import cn.ideabuffer.process.core.context.Context;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/04/29
 */
public interface ProcessListener<R> {

    void onComplete(@NotNull Context context, R result);

    void onFailure(@NotNull Context context, Throwable t);

}

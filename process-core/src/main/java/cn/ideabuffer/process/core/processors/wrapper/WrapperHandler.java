package cn.ideabuffer.process.core.processors.wrapper;

import cn.ideabuffer.process.core.context.Context;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author sangjian.sj
 * @date 2021/06/12
 */
public interface WrapperHandler<R> {

    void before(@NotNull Context context);

    @Nullable
    R afterReturning(@NotNull Context context, @Nullable R result);

    void afterThrowing(@NotNull Context context, @NotNull Throwable t) throws Throwable;

}

package cn.ideabuffer.process.core;

import cn.ideabuffer.process.core.context.Context;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface ResultHandler<R> {

    R getResult(@NotNull Context context);

}

package cn.ideabuffer.process.core;

import cn.ideabuffer.process.core.context.Context;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 结果转换器，将原结果进行转换，返回新的结果
 *
 * @param <I> 原结果类型
 * @param <O> 目标结果类型
 */
@FunctionalInterface
public interface ResultConverter<I, O> {

    @Nullable
    O convert(@NotNull Context context, @Nullable I origin);
}

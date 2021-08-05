package cn.ideabuffer.process.core;

import cn.ideabuffer.process.core.context.Context;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author sangjian.sj
 * @date 2020/04/30
 */
@FunctionalInterface
public interface Processor<V> {

    @Nullable
    V process(@NotNull Context context) throws Exception;

}

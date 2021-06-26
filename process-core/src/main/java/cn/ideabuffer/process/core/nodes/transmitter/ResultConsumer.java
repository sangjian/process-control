package cn.ideabuffer.process.core.nodes.transmitter;

import cn.ideabuffer.process.core.context.Context;
import org.jetbrains.annotations.Nullable;

/**
 * @author sangjian.sj
 * @date 2020/03/08
 */
@FunctionalInterface
public interface ResultConsumer<T> {

    void accept(Context context, @Nullable T result);

}

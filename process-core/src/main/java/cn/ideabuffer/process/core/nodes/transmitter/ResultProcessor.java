package cn.ideabuffer.process.core.nodes.transmitter;

import cn.ideabuffer.process.core.context.Context;
import org.jetbrains.annotations.Nullable;

/**
 * @author sangjian.sj
 * @date 2020/03/08
 */
@FunctionalInterface
public interface ResultProcessor<R, P> {

    R apply(Context context, @Nullable P result);

}

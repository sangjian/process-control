package cn.ideabuffer.process.core.processors.wrapper.proxy;

import cn.ideabuffer.process.core.processors.DistributeProcessor;
import cn.ideabuffer.process.core.processors.wrapper.WrapperHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2021/06/17
 */
public class DistributeProcessorProxy<T, R> extends AbstractProcessorProxy<DistributeProcessor<T, R>, T>
    implements DistributeProcessor<T, R> {

    public DistributeProcessorProxy(
        @NotNull DistributeProcessor<T, R> target,
        @NotNull WrapperHandler<T> handler) {
        super(target, handler);
    }

    public static <T, R> DistributeProcessor<T, R> wrap(@NotNull DistributeProcessor<T, R> target,
        List<WrapperHandler<T>> handlers) {
        if (handlers == null || handlers.isEmpty()) {
            return target;
        }
        DistributeProcessor<T, R> wrapped = target;
        for (int i = handlers.size() - 1; i >= 0; i--) {
            wrapped = new DistributeProcessorProxy<>(wrapped, handlers.get(i));
        }
        return wrapped;
    }

    @Override
    public R merge(T t, @NotNull R r) {return getTarget().merge(t, r);}
}

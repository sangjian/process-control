package cn.ideabuffer.process.core.processors.wrapper.proxy;

import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.processors.wrapper.WrapperHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2021/06/18
 */
public class DefaultProcessorProxy<R> extends AbstractProcessorProxy<Processor<R>, R> {

    public DefaultProcessorProxy(@NotNull Processor<R> target, WrapperHandler<R> handler) {
        super(target, handler);
    }

    public static <R> Processor<R> wrap(@NotNull Processor<R> target, List<WrapperHandler<R>> handlers) {
        if (handlers == null || handlers.isEmpty()) {
            return target;
        }
        Processor<R> wrapped = target;
        for (int i = handlers.size() - 1; i >= 0; i--) {
            wrapped = new DefaultProcessorProxy<>(wrapped, handlers.get(i));
        }
        return wrapped;
    }
}

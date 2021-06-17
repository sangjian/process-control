package cn.ideabuffer.process.core.processors.wrapper.proxy;

import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.processors.wrapper.ProcessorProxy;
import cn.ideabuffer.process.core.processors.wrapper.WrapperHandler;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2021/06/17
 */
public abstract class AbstractProcessorProxy<P extends Processor<R>, R> implements Processor<R>, ProcessorProxy<P, R> {

    private P target;

    private WrapperHandler<R> handler;

    public AbstractProcessorProxy(@NotNull P target, @NotNull WrapperHandler<R> handler) {
        this.target = target;
        this.handler = handler;
    }

    @Override
    public P getTarget() {
        return target;
    }

    @Override
    public WrapperHandler<R> getHandler() {
        return handler;
    }

    @Override
    public R process(@NotNull Context context) throws Exception {
        return null;
    }
}

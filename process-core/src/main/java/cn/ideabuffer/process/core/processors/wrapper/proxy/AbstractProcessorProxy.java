package cn.ideabuffer.process.core.processors.wrapper.proxy;

import cn.ideabuffer.process.core.Lifecycle;
import cn.ideabuffer.process.core.LifecycleManager;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.exceptions.ProcessException;
import cn.ideabuffer.process.core.processors.wrapper.WrapperHandler;
import cn.ideabuffer.process.core.processors.wrapper.exception.WrapperHandlerProcessException;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2021/06/17
 */
public abstract class AbstractProcessorProxy<P extends Processor<R>, R> implements Processor<R>, ProcessorProxy<P, R>,
    Lifecycle {

    private P target;

    private WrapperHandler<R> handler;

    protected AbstractProcessorProxy(@NotNull P target, @NotNull WrapperHandler<R> handler) {
        this.target = target;
        this.handler = handler;
    }

    @NotNull
    @Override
    public P getTarget() {
        return target;
    }

    @NotNull
    @Override
    public WrapperHandler<R> getHandler() {
        return handler;
    }

    @Override
    public R process(@NotNull Context context) throws Exception {
        R result;
        try {
            handler.before(context);
            result = target.process(context);
        } catch (Throwable t) {
            try {
                handler.afterThrowing(context, t);
                return null;
            } catch (Throwable at) {
                // 如果抛出的异常是Processor抛出的异常，则使用ProcessException
                // 否则是afterThrowing中执行抛出的异常，使用WrapperHandlerProcessException
                if (at == t || at == t.getCause()) {
                    throw new ProcessException("process error!", t);
                } else {
                    throw new WrapperHandlerProcessException("afterThrowing error!", t);
                }
            }
        }
        try {
            result = handler.afterReturning(context, result);
        } catch (Throwable t) {
            throw new WrapperHandlerProcessException("afterReturning error!", t);
        }
        return result;
    }

    @Override
    public void initialize() {
        if (this.target instanceof Lifecycle) {
            LifecycleManager.initialize((Lifecycle)this.target);
        }
    }

    @Override
    public void destroy() {
        if (this.target instanceof Lifecycle) {
            LifecycleManager.destroy((Lifecycle)this.target);
        }
    }
}

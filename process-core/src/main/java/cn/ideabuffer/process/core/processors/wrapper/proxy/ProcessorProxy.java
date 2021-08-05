package cn.ideabuffer.process.core.processors.wrapper.proxy;

import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.processors.wrapper.WrapperHandler;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2021/06/17
 */
public interface ProcessorProxy<P extends Processor<R>, R> {

    @NotNull
    P getTarget();

    @NotNull
    WrapperHandler<R> getHandler();

}

package cn.ideabuffer.process.core.processors.wrapper;

import cn.ideabuffer.process.core.Processor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

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

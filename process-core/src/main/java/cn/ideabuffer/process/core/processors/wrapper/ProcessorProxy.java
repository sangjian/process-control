package cn.ideabuffer.process.core.processors.wrapper;

import cn.ideabuffer.process.core.Processor;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2021/06/17
 */
public interface ProcessorProxy<P extends Processor<R>, R> {

    P getTarget();

    WrapperHandler<R> getHandler();

}

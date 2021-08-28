package cn.ideabuffer.process.core.processors;

import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.ResultConverter;
import org.jetbrains.annotations.NotNull;

/**
 * 转换处理器，对原结果进行二次封装，返回目标结果类型
 *
 * @param <I> 原结果类型
 * @param <O> 目标结果类型
 */
public interface ConvertProcessor<I, O> extends Processor<O> {

    @NotNull
    Processor<I> getProcessor();

    @NotNull
    ResultConverter<I, O> getConverter();

}

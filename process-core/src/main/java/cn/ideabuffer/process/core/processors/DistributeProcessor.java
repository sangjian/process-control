package cn.ideabuffer.process.core.processors;

import cn.ideabuffer.process.core.Processor;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/05/09
 */
public interface DistributeProcessor<T, R> extends Processor<T> {

    R merge(T t, @NotNull R r);
}

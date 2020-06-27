package cn.ideabuffer.process.core.processors;

import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/05/09
 */
public interface DistributeProcessor<T, R> extends ComplexProcessor<T> {

    R merge(T t, @NotNull R r);
}

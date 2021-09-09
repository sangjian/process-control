package cn.ideabuffer.process.core.processors;

import org.jetbrains.annotations.NotNull;

public interface DistributeMerger<T, R> {

    /**
     * 分布式结果合并
     *
     * @param t 当前处理器返回结果
     * @param r 分布式聚合结果对象
     * @return 聚合后的结果
     */
    R merge(T t, @NotNull R r);
}

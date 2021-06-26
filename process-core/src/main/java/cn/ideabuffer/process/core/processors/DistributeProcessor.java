package cn.ideabuffer.process.core.processors;

import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.aggregators.DistributeAggregator;
import cn.ideabuffer.process.core.nodes.DistributeMergeableNode;
import org.jetbrains.annotations.NotNull;

/**
 * 分布式处理器，在分布式聚合节点中使用，用于分布式聚合结果。
 *
 * @param <T> 当前处理器返回的结果类型
 * @param <R> 分布式聚合器聚合结果类型
 * @author sangjian.sj
 * @date 2020/05/09
 * @see DistributeAggregator
 * @see DistributeMergeableNode
 */
public interface DistributeProcessor<T, R> extends Processor<T> {

    /**
     * 分布式结果合并
     *
     * @param t 当前处理器返回结果
     * @param r 分布式聚合结果对象
     * @return 聚合后的结果
     */
    R merge(T t, @NotNull R r);
}

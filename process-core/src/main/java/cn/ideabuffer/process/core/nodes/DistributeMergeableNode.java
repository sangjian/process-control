package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.processors.DistributeProcessor;

/**
 * 分布式可合并结果的节点
 *
 * @param <T> 当前处理器返回的结果类型
 * @param <R> 分布式聚合器聚合结果类型
 * @author sangjian.sj
 * @date 2020/03/07
 * @see DistributeProcessor
 */
public interface DistributeMergeableNode<T, R> extends MergeableNode<DistributeProcessor<T, R>, T> {

}

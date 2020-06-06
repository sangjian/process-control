package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.processors.DistributeProcessor;

/**
 * 可合并结果的节点
 *
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface DistributeMergeableNode<T, R> extends MergeableNode {

    void registerProcessor(DistributeProcessor<T, R> processor);

    DistributeProcessor<T, R> getProcessor();
}

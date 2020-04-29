package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.DistributeMergeable;

/**
 * 可合并结果的节点
 *
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface DistributeMergeableNode<T, R> extends MergeableNode<T>, DistributeMergeable<T, R> {

}

package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.Processor;

/**
 * 可合并结果的节点
 *
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface GenericMergeableNode<R> extends MergeableNode<Processor<R>, R> {

}

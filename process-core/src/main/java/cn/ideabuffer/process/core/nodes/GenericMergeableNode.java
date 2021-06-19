package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.Processor;

/**
 * 通用可合并结果的节点
 *
 * @param <R> 处理器返回结果类型
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface GenericMergeableNode<R> extends MergeableNode<Processor<R>, R> {

}

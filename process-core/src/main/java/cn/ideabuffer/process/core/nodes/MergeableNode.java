package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.Matchable;
import cn.ideabuffer.process.core.Mergeable;
import cn.ideabuffer.process.core.Node;
import cn.ideabuffer.process.core.Processor;

/**
 * 可合并结果的节点
 *
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface MergeableNode<R> extends Node, Mergeable, Matchable {

    void registerProcessor(Processor<R> processor);

    Processor<R> getProcessor();
}

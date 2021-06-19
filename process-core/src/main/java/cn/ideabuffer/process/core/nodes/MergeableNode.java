package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.Matchable;
import cn.ideabuffer.process.core.Mergeable;
import cn.ideabuffer.process.core.Node;
import cn.ideabuffer.process.core.Processor;

/**
 * 可合并结果的节点
 *
 * @param <P> 节点处理器
 * @param <R> 处理器返回类型
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface MergeableNode<P extends Processor<R>, R> extends Node, Mergeable, Matchable {

    /**
     * 注册处理器
     *
     * @param processor 处理器
     */
    void registerProcessor(P processor);

    /**
     * 获取处理器
     *
     * @return 已注册处理器
     */
    P getProcessor();
}

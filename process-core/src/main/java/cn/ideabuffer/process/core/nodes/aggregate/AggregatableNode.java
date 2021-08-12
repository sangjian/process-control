package cn.ideabuffer.process.core.nodes.aggregate;

import cn.ideabuffer.process.core.ComplexNodes;
import cn.ideabuffer.process.core.Parallelizable;
import cn.ideabuffer.process.core.nodes.GenericMergeableNode;
import cn.ideabuffer.process.core.nodes.TransmittableNode;
import cn.ideabuffer.process.core.processors.AggregateProcessor;

import java.util.List;

/**
 * 可聚合结果的节点，同时该类型节点支持结果传递。
 *
 * @param <I> 输入类型
 * @param <O> 聚合结果类型
 * @author sangjian.sj
 * @date 2020/03/07
 * @see AggregateProcessor
 * @see TransmittableNode
 */
public interface AggregatableNode<I, O>
    extends TransmittableNode<O, AggregateProcessor<I, O>>, Parallelizable, ComplexNodes<GenericMergeableNode<I>> {

    /**
     * 聚合执行超时时间
     *
     * @param timeout 超时时间，单位：毫秒
     */
    void timeout(long timeout);

    /**
     * 获取超时时间
     *
     * @return 超时时间，单位：毫秒
     */
    long getTimeout();

    List<GenericMergeableNode<I>> getMergeableNodes();

}

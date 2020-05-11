package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.Parallelizable;
import cn.ideabuffer.process.core.aggregator.Aggregator;
import cn.ideabuffer.process.core.processors.AggregateProcessor;

import java.util.List;

/**
 * 可聚合结果的节点
 *
 * @param <I> 可合并结果类型
 * @param <O> 聚合结果类型
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface AggregatableNode<I, O>
    extends TransmittableNode<O, AggregateProcessor<I, O>>, Parallelizable {

}

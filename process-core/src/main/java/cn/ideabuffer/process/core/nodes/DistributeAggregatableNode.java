package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.Parallelizable;
import cn.ideabuffer.process.core.nodes.TransmittableNode;
import cn.ideabuffer.process.core.processors.DistributeAggregateProcessor;

/**
 * 可聚合结果的节点
 *
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface DistributeAggregatableNode<O> extends TransmittableNode<O, DistributeAggregateProcessor<O>>, Parallelizable {

    void timeout(long timeout);

    long getTimeout();

}

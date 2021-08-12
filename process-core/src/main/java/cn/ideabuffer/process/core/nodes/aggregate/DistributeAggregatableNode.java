package cn.ideabuffer.process.core.nodes.aggregate;

import cn.ideabuffer.process.core.ComplexNodes;
import cn.ideabuffer.process.core.Parallelizable;
import cn.ideabuffer.process.core.nodes.DistributeMergeableNode;
import cn.ideabuffer.process.core.nodes.TransmittableNode;
import cn.ideabuffer.process.core.processors.DistributeAggregateProcessor;

import java.util.List;

/**
 * 分布式可聚合节点
 *
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface DistributeAggregatableNode<O>
    extends TransmittableNode<O, DistributeAggregateProcessor<O>>, Parallelizable, ComplexNodes<DistributeMergeableNode<?, O>> {

    void timeout(long timeout);

    long getTimeout();

    List<DistributeMergeableNode<?, O>> getMergeableNodes();

}

package cn.ideabuffer.process.core.processors;

import cn.ideabuffer.process.core.aggregator.DistributeAggregator;
import cn.ideabuffer.process.core.nodes.DistributeMergeableNode;

/**
 * @author sangjian.sj
 * @date 2020/05/02
 */
public class DistributeAggregateProcessor<R> extends DefaultAggregateProcessor<DistributeAggregator<R>, DistributeMergeableNode<?, R>, R> {
}

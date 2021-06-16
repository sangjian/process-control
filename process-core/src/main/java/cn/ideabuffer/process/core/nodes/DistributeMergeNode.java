package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.processors.DistributeProcessor;
import cn.ideabuffer.process.core.rule.Rule;

/**
 * @author sangjian.sj
 * @date 2020/05/09
 */
public class DistributeMergeNode<T, R> extends AbstractMergeableNode implements DistributeMergeableNode<T, R> {

    private DistributeProcessor<T, R> processor;

    public DistributeMergeNode() {
    }

    public DistributeMergeNode(Rule rule, long timeout, DistributeProcessor<T, R> processor) {
        super(rule, timeout);
        this.processor = processor;
    }

    @Override
    public void registerProcessor(DistributeProcessor<T, R> processor) {
        this.processor = processor;
    }

    @Override
    public DistributeProcessor<T, R> getProcessor() {
        return processor;
    }
}

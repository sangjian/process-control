package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.rule.Rule;

/**
 * @author sangjian.sj
 * @date 2020/05/09
 */
public class DefaultGenericMergeableNode<R> extends AbstractMergeableNode implements GenericMergeableNode<R> {

    private Processor<R> processor;

    public DefaultGenericMergeableNode() {
    }

    public DefaultGenericMergeableNode(Rule rule, long timeout, Processor<R> processor) {
        super(rule, timeout);
        this.processor = processor;
    }

    @Override
    public void registerProcessor(Processor<R> processor) {
        this.processor = processor;
    }

    @Override
    public Processor<R> getProcessor() {
        return processor;
    }

    public void setProcessor(Processor<R> processor) {
        this.processor = processor;
    }
}

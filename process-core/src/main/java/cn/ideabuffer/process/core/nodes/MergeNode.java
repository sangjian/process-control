package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.handler.ExceptionHandler;
import cn.ideabuffer.process.core.rule.Rule;

/**
 * @author sangjian.sj
 * @date 2020/05/09
 */
public class MergeNode<R> extends AbstractMergeableNode implements GenericMergeableNode<R> {

    private Processor<R> processor;

    public MergeNode() {
    }

    public MergeNode(Rule rule,
        ExceptionHandler exceptionHandler, long timeout,
        Processor<R> processor) {
        super(rule, exceptionHandler, timeout);
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

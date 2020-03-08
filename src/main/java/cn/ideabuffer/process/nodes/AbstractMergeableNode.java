package cn.ideabuffer.process.nodes;

import cn.ideabuffer.process.MergeableNode;
import cn.ideabuffer.process.handler.ExceptionHandler;
import cn.ideabuffer.process.rule.Rule;

/**
 * @author sangjian.sj
 * @date 2020/03/07
 */
public abstract class AbstractMergeableNode<T> extends AbstractNode implements MergeableNode<T> {

    private Rule rule;

    private ExceptionHandler exceptionHandler;

    @Override
    public MergeableNode<T> exceptionHandler(ExceptionHandler handler) {
        this.exceptionHandler = handler;
        return this;
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        return exceptionHandler;
    }

    @Override
    public MergeableNode<T> processOn(Rule rule) {
        this.rule = rule;
        return this;
    }

    @Override
    public Rule getRule() {
        return rule;
    }
}

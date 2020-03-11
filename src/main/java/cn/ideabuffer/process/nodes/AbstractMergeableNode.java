package cn.ideabuffer.process.nodes;

import cn.ideabuffer.process.Context;
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

    @Override
    public T invoke(Context context) throws Exception {
        try {
            return doInvoke(context);
        } catch (Exception e) {
            if (exceptionHandler != null) {
                exceptionHandler.handle(e);
            } else {
                throw e;
            }
        }
        return null;
    }

    protected abstract T doInvoke(Context context) throws Exception;
}

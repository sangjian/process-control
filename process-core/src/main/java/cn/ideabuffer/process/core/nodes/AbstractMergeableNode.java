package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.handler.ExceptionHandler;
import cn.ideabuffer.process.core.rule.Rule;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author sangjian.sj
 * @date 2020/03/07
 */
public abstract class AbstractMergeableNode<T> extends AbstractNode implements MergeableNode<T> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Rule rule;
    private ExceptionHandler exceptionHandler;
    private long timeout;

    @Override
    public void exceptionHandler(ExceptionHandler handler) {
        this.exceptionHandler = handler;
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        return exceptionHandler;
    }

    public void setExceptionHandler(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public void processOn(Rule rule) {
        this.rule = rule;
    }

    @Override
    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    @Override
    public void timeout(long timeout, @NotNull TimeUnit unit) {
        this.timeout = unit.toMillis(timeout);
    }

    @Override
    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
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
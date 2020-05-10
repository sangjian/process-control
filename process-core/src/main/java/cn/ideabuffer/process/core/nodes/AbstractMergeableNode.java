package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.Matchable;
import cn.ideabuffer.process.core.Mergeable;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.handler.ExceptionHandler;
import cn.ideabuffer.process.core.rule.Rule;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author sangjian.sj
 * @date 2020/03/07
 */
public abstract class AbstractMergeableNode extends AbstractNode implements Mergeable, Matchable {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Rule rule;
    private ExceptionHandler exceptionHandler;
    private long timeout;

    public AbstractMergeableNode() {
    }

    public AbstractMergeableNode(Rule rule, ExceptionHandler exceptionHandler, long timeout) {
        this.rule = rule;
        this.exceptionHandler = exceptionHandler;
        this.timeout = timeout;
    }

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



}

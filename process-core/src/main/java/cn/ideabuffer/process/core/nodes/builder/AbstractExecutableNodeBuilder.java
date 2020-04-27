package cn.ideabuffer.process.core.nodes.builder;

import cn.ideabuffer.process.core.handler.ExceptionHandler;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.rule.Rule;

import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/04/24
 */
public abstract class AbstractExecutableNodeBuilder<T extends ExecutableNode> implements Builder<T> {

    protected ExceptionHandler handler;

    protected boolean parallel;

    protected Executor executor;

    protected Rule rule;

    protected T node;

    protected AbstractExecutableNodeBuilder(T node) {
        this.node = node;
    }

    public Builder<T> exceptionHandler(ExceptionHandler handler) {
        this.handler = handler;
        return this;
    }

    public Builder<T> parallel() {
        this.parallel = true;
        return this;
    }

    public Builder<T> parallel(Executor executor) {
        this.executor = executor;
        return this;
    }

    public Builder<T> processOn(Rule rule) {
        this.rule = rule;
        return this;
    }

    @Override
    public T build() {
        if (parallel) {
            node.parallel();
        }
        if (executor != null) {
            node.parallel(executor);
        }
        node.processOn(rule);
        node.exceptionHandler(handler);
        return node;
    }

}

package cn.ideabuffer.process.core.nodes.builder;

import cn.ideabuffer.process.core.ProcessListener;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.rule.Rule;

import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/04/24
 */
public abstract class AbstractExecutableNodeBuilder<R, P extends Processor<R>, T extends ExecutableNode<R, P>> implements Builder<T> {

    protected boolean parallel;

    protected Executor executor;

    protected Rule rule;

    protected ProcessListener[] listeners;

    protected T node;

    protected P processor;

    protected AbstractExecutableNodeBuilder(T node) {
        this.node = node;
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

    public Builder<T> addListeners(ProcessListener... listeners) {
        this.listeners = listeners;
        return this;
    }

    public Builder<T> by(P processor) {
        this.processor = processor;
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
        if (listeners != null) {
            node.addProcessListeners(listeners);
        }
        node.registerProcessor(processor);
        return node;
    }

}

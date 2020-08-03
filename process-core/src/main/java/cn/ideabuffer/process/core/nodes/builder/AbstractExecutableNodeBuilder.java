package cn.ideabuffer.process.core.nodes.builder;

import cn.ideabuffer.process.core.ProcessListener;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.ReturnCondition;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.rule.Rule;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/**
 * @author sangjian.sj
 * @date 2020/04/24
 */
public abstract class AbstractExecutableNodeBuilder<R, P extends Processor<R>, T extends ExecutableNode<R, P>>
    implements Builder<T> {

    protected boolean parallel;

    protected Executor executor;

    protected Rule rule;

    protected List<ProcessListener<R>> listeners;

    protected T node;

    protected P processor;

    protected Key<R> resultKey;

    protected boolean returnable;

    protected ReturnCondition<R> returnCondition;

    protected AbstractExecutableNodeBuilder(T node) {
        this.node = node;
        this.listeners = new LinkedList<>();
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

    @SuppressWarnings("unchecked")
    public Builder<T> addListeners(ProcessListener<R>... listeners) {
        if (listeners != null) {
            this.listeners.addAll(Arrays.stream(listeners).collect(Collectors.toList()));
        }
        return this;
    }

    public Builder<T> by(P processor) {
        this.processor = processor;
        return this;
    }

    public Builder<T> resultKey(Key<R> resultKey) {
        this.resultKey = resultKey;
        return this;
    }

    public Builder<T> returnable(boolean returnable) {
        this.returnable = returnable;
        return this;
    }

    public Builder<T> returnOn(ReturnCondition<R> condition) {
        this.returnCondition = condition;
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
            //noinspection unchecked
            node.addProcessListeners(listeners.toArray(new ProcessListener[0]));
        }
        node.registerProcessor(processor);
        node.setResultKey(resultKey);
        node.returnable(returnable);
        node.returnOn(returnCondition);
        return node;
    }

}

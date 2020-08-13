package cn.ideabuffer.process.core.nodes.builder;

import cn.ideabuffer.process.core.ProcessListener;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.ReturnCondition;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.nodes.ProcessNode;
import cn.ideabuffer.process.core.rule.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/**
 * @author sangjian.sj
 * @date 2020/04/24
 */
public class ProcessNodeBuilder<R> extends AbstractExecutableNodeBuilder<R, Processor<R>, ProcessNode<R>> {

    private ProcessNodeBuilder() {
        super(new ProcessNode<>());
    }

    public static <R> ProcessNodeBuilder<R> newBuilder() {
        return new ProcessNodeBuilder<>();
    }

    @Override
    public ProcessNodeBuilder<R> parallel() {
        super.parallel();
        return this;
    }

    @Override
    public ProcessNodeBuilder<R> parallel(Executor executor) {
        super.parallel(executor);
        return this;
    }

    @Override
    public ProcessNodeBuilder<R> processOn(Rule rule) {
        super.processOn(rule);
        return this;
    }

    @Override
    public ProcessNodeBuilder<R> addListeners(ProcessListener<R>... listeners) {
        super.addListeners(listeners);
        return this;
    }

    @Override
    public ProcessNodeBuilder<R> by(Processor<R> processor) {
        super.by(processor);
        return this;
    }

    @Override
    public ProcessNodeBuilder<R> resultKey(Key<R> resultKey) {
        super.resultKey(resultKey);
        return this;
    }

    @Override
    public ProcessNodeBuilder<R> returnOn(ReturnCondition<R> condition) {
        super.returnOn(condition);
        return this;
    }

    @Override
    public ProcessNodeBuilder<R> require(@NotNull Key<?> key) {
        super.require(key);
        return this;
    }

    @Override
    public ProcessNodeBuilder<R> require(@NotNull Key<?>... keys) {
        super.require(Arrays.stream(keys).collect(Collectors.toSet()));
        return this;
    }

    @Override
    public ProcessNodeBuilder<R> require(@NotNull Set<Key<?>> keys) {
        super.require(keys);
        return this;
    }
}

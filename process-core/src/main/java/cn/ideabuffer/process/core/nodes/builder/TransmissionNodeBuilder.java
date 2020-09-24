package cn.ideabuffer.process.core.nodes.builder;

import cn.ideabuffer.process.core.ProcessListener;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.ReturnCondition;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.nodes.TransmissionNode;
import cn.ideabuffer.process.core.rule.Rule;

import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;

/**
 * @author sangjian.sj
 * @date 2020/04/24
 */
public class TransmissionNodeBuilder<R>
    extends AbstractExecutableNodeBuilder<R, Processor<R>, TransmissionNode<R>> {

    private TransmissionNodeBuilder(TransmissionNode<R> node) {
        super(node);
    }

    public static <R> TransmissionNodeBuilder<R> newBuilder() {
        return new TransmissionNodeBuilder<>(new TransmissionNode<>());
    }

    @Override
    public TransmissionNodeBuilder<R> parallel() {
        super.parallel();
        return this;
    }

    @Override
    public TransmissionNodeBuilder<R> parallel(Executor executor) {
        super.parallel(executor);
        return this;
    }

    @Override
    public TransmissionNodeBuilder<R> processOn(Rule rule) {
        super.processOn(rule);
        return this;
    }

    @Override
    public TransmissionNodeBuilder<R> addListeners(ProcessListener<R>... listeners) {
        super.addListeners(listeners);
        return this;
    }

    @Override
    public TransmissionNodeBuilder<R> by(Processor<R> processor) {
        super.by(processor);
        return this;
    }

    @Override
    public TransmissionNodeBuilder<R> resultKey(Key<R> resultKey) {
        super.resultKey(resultKey);
        return this;
    }

    @Override
    public TransmissionNodeBuilder<R> returnOn(ReturnCondition<R> condition) {
        super.returnOn(condition);
        return this;
    }

    @Override
    public TransmissionNodeBuilder<R> enabled(BooleanSupplier supplier) {
        super.enabled(supplier);
        return this;
    }

}
package cn.ideabuffer.process.core.nodes.builder;

import cn.ideabuffer.process.core.ProcessListener;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.ReturnCondition;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.nodes.TransmittableNode;
import cn.ideabuffer.process.core.rule.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;

/**
 * @author sangjian.sj
 * @date 2020/04/24
 */
public class TransmittableNodeBuilder<R, P extends Processor<R>>
    extends AbstractExecutableNodeBuilder<R, P, TransmittableNode<R, P>> {

    private TransmittableNodeBuilder(TransmittableNode<R, P> node) {
        super(node);
    }

    public static <R, P extends Processor<R>> TransmittableNodeBuilder<R, P> newBuilder(
        @NotNull TransmittableNode<R, P> node) {
        return new TransmittableNodeBuilder<>(node);
    }

    @Override
    public TransmittableNodeBuilder<R, P> parallel() {
        super.parallel();
        return this;
    }

    @Override
    public TransmittableNodeBuilder<R, P> parallel(Executor executor) {
        super.parallel(executor);
        return this;
    }

    @Override
    public TransmittableNodeBuilder<R, P> processOn(Rule rule) {
        super.processOn(rule);
        return this;
    }

    @Override
    public TransmittableNodeBuilder<R, P> addListeners(ProcessListener<R>... listeners) {
        super.addListeners(listeners);
        return this;
    }

    @Override
    public TransmittableNodeBuilder<R, P> by(P processor) {
        super.by(processor);
        return this;
    }

    @Override
    public TransmittableNodeBuilder<R, P> resultKey(Key<R> resultKey) {
        super.resultKey(resultKey);
        return this;
    }

    @Override
    public TransmittableNodeBuilder<R, P> returnOn(ReturnCondition<R> condition) {
        super.returnOn(condition);
        return this;
    }

    @Override
    public TransmittableNodeBuilder<R, P> enabled(BooleanSupplier supplier) {
        super.enabled(supplier);
        return this;
    }

}

package cn.ideabuffer.process.core.nodes.builder;

import cn.ideabuffer.process.core.handler.ExceptionHandler;
import cn.ideabuffer.process.core.nodes.TransmittableNode;
import cn.ideabuffer.process.core.rule.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/04/24
 */
public class TransmittableNodeBuilder<R> extends AbstractExecutableNodeBuilder<TransmittableNode<R>> {

    private TransmittableNodeBuilder(TransmittableNode<R> node) {
        super(node);
    }

    public static <R> TransmittableNodeBuilder newBuilder(@NotNull TransmittableNode<R> node) {
        return new TransmittableNodeBuilder<>(node);
    }

    @Override
    public TransmittableNodeBuilder<R> exceptionHandler(ExceptionHandler handler) {
        super.exceptionHandler(handler);
        return this;
    }

    @Override
    public TransmittableNodeBuilder<R> parallel() {
        super.parallel();
        return this;
    }

    @Override
    public TransmittableNodeBuilder<R> parallel(Executor executor) {
        super.parallel(executor);
        return this;
    }

    @Override
    public TransmittableNodeBuilder<R> processOn(Rule rule) {
        super.processOn(rule);
        return this;
    }

}

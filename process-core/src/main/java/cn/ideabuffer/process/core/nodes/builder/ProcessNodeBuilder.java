package cn.ideabuffer.process.core.nodes.builder;

import cn.ideabuffer.process.core.NodeListener;
import cn.ideabuffer.process.core.ProcessListener;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.nodes.ProcessNode;
import cn.ideabuffer.process.core.rule.Rule;

import java.util.concurrent.Executor;

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
    public ProcessNodeBuilder<R> nodeListener(NodeListener<R> nodeListener) {
        super.nodeListener(nodeListener);
        return this;
    }

    @Override
    public ProcessNodeBuilder<R> by(Processor<R> processor) {
        super.by(processor);
        return this;
    }
}

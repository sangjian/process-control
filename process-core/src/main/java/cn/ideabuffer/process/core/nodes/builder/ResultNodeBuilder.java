package cn.ideabuffer.process.core.nodes.builder;

import cn.ideabuffer.process.core.NodeListener;
import cn.ideabuffer.process.core.ProcessListener;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.nodes.DefaultResultNode;
import cn.ideabuffer.process.core.nodes.ResultNode;
import cn.ideabuffer.process.core.rule.Rule;

import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/07/21
 */
public class ResultNodeBuilder<R> extends AbstractExecutableNodeBuilder<R, Processor<R>, ResultNode<R, Processor<R>>> {

    private Class<R> resultClass;

    private ResultNodeBuilder() {
        super(new DefaultResultNode<>());
    }

    public static <R> ResultNodeBuilder<R> newBuilder() {
        return new ResultNodeBuilder<>();
    }

    @Override
    public ResultNodeBuilder<R> parallel() {
        super.parallel();
        return this;
    }

    @Override
    public ResultNodeBuilder<R> parallel(Executor executor) {
        super.parallel(executor);
        return this;
    }

    @Override
    public ResultNodeBuilder<R> processOn(Rule rule) {
        super.processOn(rule);
        return this;
    }

    @Override
    public ResultNodeBuilder<R> addListeners(ProcessListener<R>... listeners) {
        super.addListeners(listeners);
        return this;
    }

    @Override
    public ResultNodeBuilder<R> nodeListener(NodeListener<R> nodeListener) {
        super.nodeListener(nodeListener);
        return this;
    }

    @Override
    public ResultNodeBuilder<R> by(Processor<R> processor) {
        super.by(processor);
        return this;
    }

    public ResultNodeBuilder<R> resultClass(Class<R> resultClass) {
        this.resultClass = resultClass;
        return this;
    }

    @Override
    public ResultNode<R, Processor<R>> build() {
        ResultNode<R, Processor<R>> resultNode = super.build();
        resultNode.setResultClass(resultClass);
        return resultNode;
    }
}

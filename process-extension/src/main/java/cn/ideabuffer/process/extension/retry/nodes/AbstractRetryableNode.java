package cn.ideabuffer.process.extension.retry.nodes;

import cn.ideabuffer.process.core.NodeListener;
import cn.ideabuffer.process.core.ProcessListener;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.core.rule.Rule;
import cn.ideabuffer.process.extension.retry.processors.RetryProcessor;
import com.github.rholder.retry.Retryer;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/04/27
 */
public abstract class AbstractRetryableNode<R> extends AbstractExecutableNode<R, Processor<R>> implements
    RetryableNode<R> {

    private Retryer<R> retryer;

    private RetryProcessor<R> retryProcessor;

    public AbstractRetryableNode(Retryer<R> retryer) {
        this(false, retryer);
    }

    public AbstractRetryableNode(boolean parallel, Retryer<R> retryer) {
        this(parallel, null, retryer);
    }

    public AbstractRetryableNode(Rule rule, Retryer<R> retryer) {
        this(false, rule, null, retryer);
    }

    public AbstractRetryableNode(boolean parallel, Executor executor, Retryer<R> retryer) {
        this(parallel, null, executor, retryer);
    }

    public AbstractRetryableNode(boolean parallel, Rule rule, Executor executor,
        Retryer<R> retryer) {
        this(parallel, rule, executor, null, null, null, retryer);
    }

    public AbstractRetryableNode(boolean parallel, Rule rule, Executor executor,
        List<ProcessListener> listeners, NodeListener<R> nodeListener, Processor<R> processor,
        Retryer<R> retryer) {
        super(parallel, rule, executor, listeners, nodeListener, processor);
        this.retryer = retryer;
        this.retryProcessor = new RetryProcessor<>(retryer, processor);
    }

    @Override
    public Retryer<R> getRetryer() {
        return retryer;
    }

    public void setRetryer(Retryer<R> retryer) {
        this.retryer = retryer;
    }

    @Override
    public RetryProcessor<R> getRetryProcessor() {
        return retryProcessor;
    }

    public void setRetryProcessor(RetryProcessor<R> retryProcessor) {
        this.retryProcessor = retryProcessor;
    }

    @Override
    public void retryBy(Retryer<R> retryer) {
        this.retryer = retryer;
        this.retryProcessor.setRetryer(retryer);
    }

    @Override
    public void registerProcessor(Processor<R> processor) {
        this.retryProcessor.setProcessor(processor);
        super.registerProcessor(retryProcessor);
    }
}

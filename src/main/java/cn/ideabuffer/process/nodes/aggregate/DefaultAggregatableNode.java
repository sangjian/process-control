package cn.ideabuffer.process.nodes.aggregate;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.MergeableNode;
import cn.ideabuffer.process.Merger;
import cn.ideabuffer.process.handler.ExceptionHandler;
import cn.ideabuffer.process.nodes.AbstractNode;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/03/07
 */
public class DefaultAggregatableNode<T> extends AbstractNode implements AggregatableNode<T> {

    private boolean parallel = false;

    protected Executor executor;

    private Aggregator aggregator;

    private List<MergeableNode<T>> mergeableNodes;

    private Merger<T> merger;

    private ExceptionHandler handler;

    private AggregateResultProcessor processor;

    private AggregateResultConsumer consumer;

    private PostLink postProcessor;

    public DefaultAggregatableNode() {
    }

    public DefaultAggregatableNode(List<MergeableNode<T>> mergeableNodes) {
        this.mergeableNodes = mergeableNodes;
    }

    public void setParallel(boolean parallel) {
        this.parallel = parallel;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    public void setMergeableNodes(List<MergeableNode<T>> mergeableNodes) {
        this.mergeableNodes = mergeableNodes;
    }

    @Override
    public AggregatableNode<T> merge(MergeableNode<T>... nodes) {
        if(nodes == null || nodes.length == 0) {
            return this;
        }
        this.mergeableNodes.addAll(Arrays.asList(nodes));
        return this;
    }

    @Override
    public AggregatableNode<T> by(Merger<T> merger) {
        this.merger = merger;
        return this;
    }

    @Override
    public AggregatableNode<T> parallel() {
        this.parallel = true;
        return this;
    }

    @Override
    public AggregatableNode<T> parallel(Executor executor) {
        this.parallel = true;
        this.executor = executor;
        return this;
    }

    @Override
    public AggregatableNode<T> aggregator(@NotNull Aggregator aggregator) {
        this.aggregator = aggregator;
        return this;
    }

    @Override
    public Aggregator getAggregator() {
        return aggregator;
    }

    @Override
    public AggregatableNode<T> exceptionHandler(ExceptionHandler handler) {
        this.handler = handler;
        return this;
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        return handler;
    }

    @Override
    public <R> AggregatePostProcessor<R> thenApply(AggregateResultProcessor<R, T> processor) {
        PostLink<R> then = new PostLink<>(processor);
        this.postProcessor = then;
        return then;
    }

    @Override
    public AggregatePostProcessor<Void> thenAccept(AggregateResultConsumer<T> consumer) {
        PostLink<Void> then = new PostLink<>(consumer);
        this.postProcessor = then;
        return then;
    }

    @Override
    public void aggregate(Context context) throws Exception {
        T result = aggregator.aggregate(executor, merger, context, mergeableNodes);
        if(this.postProcessor != null) {
            //noinspection unchecked
            this.postProcessor.processNext(result);
        }
    }

    class PostLink<P> implements AggregatePostProcessor<P> {
        private AggregateResultProcessor processor;

        private AggregateResultConsumer consumer;

        private PostLink next;

        PostLink(AggregateResultProcessor processor) {
            this.processor = processor;
        }

        PostLink(AggregateResultConsumer consumer) {
            this.consumer = consumer;
        }

        @SuppressWarnings("unchecked")
        void processNext(P result) {
            Object r = result;
            if(processor != null) {
                r = processor.apply(result);
            }
            if(consumer != null) {
                consumer.accept(result);
            }
            if(next != null) {
                next.processNext(r);
            }
        }

        @Override
        public <R> AggregatePostProcessor<R> thenApply(AggregateResultProcessor<R, P> processor) {
            PostLink<R> then = new PostLink<>(processor);
            this.next = then;
            return then;
        }

        @Override
        public AggregatePostProcessor<Void> thenAccept(AggregateResultConsumer<P> consumer) {
            PostLink<Void> then = new PostLink<>(consumer);
            this.next = then;
            return then;
        }
    }


}

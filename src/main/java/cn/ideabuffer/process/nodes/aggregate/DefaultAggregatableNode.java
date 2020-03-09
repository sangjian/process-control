package cn.ideabuffer.process.nodes.aggregate;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.handler.ExceptionHandler;
import cn.ideabuffer.process.nodes.AbstractNode;
import cn.ideabuffer.process.nodes.AggregatableNode;
import cn.ideabuffer.process.nodes.MergeableNode;
import cn.ideabuffer.process.nodes.merger.Merger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;

import static cn.ideabuffer.process.nodes.aggregate.Aggregators.SERIAL;

/**
 * @author sangjian.sj
 * @date 2020/03/07
 */
public class DefaultAggregatableNode<T> extends AbstractNode implements AggregatableNode<T> {

    private boolean parallel = false;

    protected Executor executor;

    private Aggregator aggregator = SERIAL;

    private List<MergeableNode<T>> mergeableNodes;

    private Merger<T> merger;

    private ExceptionHandler handler;

    private PostNode postNode;

    public DefaultAggregatableNode() {
        this.mergeableNodes = new ArrayList<>();
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

    public void setAggregator(Aggregator aggregator) {
        this.aggregator = aggregator;
    }

    public void setMergeableNodes(List<MergeableNode<T>> mergeableNodes) {
        this.mergeableNodes = mergeableNodes;
    }

    public void setMerger(Merger<T> merger) {
        this.merger = merger;
    }

    public void setHandler(ExceptionHandler handler) {
        this.handler = handler;
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
    public <R> ResultPostProcessor<R> thenApply(ResultProcessor<R, T> processor) {
        PostNode<R> then = new PostNode<>(processor);
        this.postNode = then;
        return then;
    }

    @Override
    public ResultPostProcessor<Void> thenAccept(ResultConsumer<T> consumer) {
        PostNode<Void> then = new PostNode<>(consumer);
        this.postNode = then;
        return then;
    }

    @Override
    public void aggregate(Context context) throws Exception {
        T result = aggregator.aggregate(executor, merger, context, mergeableNodes);
        if(this.postNode != null) {
            //noinspection unchecked
            this.postNode.fire(context, result);
        }
    }

    class PostNode<P> implements ResultPostProcessor<P> {
        private ResultProcessor processor;

        private ResultConsumer consumer;

        private PostNode next;

        PostNode(ResultProcessor processor) {
            this.processor = processor;
        }

        PostNode(ResultConsumer consumer) {
            this.consumer = consumer;
        }

        @SuppressWarnings("unchecked")
        void fire(Context context, P result) {
            Object r = result;
            if(processor != null) {
                r = processor.apply(context, result);
            }
            if(consumer != null) {
                consumer.accept(context, result);
            }
            if(next != null) {
                next.fire(context, r);
            }
        }

        @Override
        public <R> ResultPostProcessor<R> thenApply(ResultProcessor<R, P> processor) {
            PostNode<R> then = new PostNode<>(processor);
            this.next = then;
            return then;
        }

        @Override
        public ResultPostProcessor<Void> thenAccept(ResultConsumer<P> consumer) {
            PostNode<Void> then = new PostNode<>(consumer);
            this.next = then;
            return then;
        }
    }


}

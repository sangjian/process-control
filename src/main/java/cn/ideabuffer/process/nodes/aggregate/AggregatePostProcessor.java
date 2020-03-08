package cn.ideabuffer.process.nodes.aggregate;

/**
 * @author sangjian.sj
 * @date 2020/03/08
 */
public interface AggregatePostProcessor<T> {

    <R> AggregatePostProcessor<R> thenApply(AggregateResultProcessor<R, T> processor);

    AggregatePostProcessor<Void> thenAccept(AggregateResultConsumer<T> consumer);

}

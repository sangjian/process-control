package cn.ideabuffer.process.nodes.transmitter;

/**
 * @author sangjian.sj
 * @date 2020/03/08
 */
public interface ResultStream<P> {

    <R> ResultStream<R> thenApply(ResultProcessor<R, P> processor);

    ResultStream<Void> thenAccept(ResultConsumer<P> consumer);

}

package cn.ideabuffer.process.nodes.transmitter;

import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/03/08
 */
public interface ResultStream<P> {

    <R> ResultStream<R> thenApply(@NotNull ResultProcessor<R, P> processor);

    <R> ResultStream<R> thenApplyAsync(@NotNull ResultProcessor<R, P> processor);

    ResultStream<Void> thenAccept(@NotNull ResultConsumer<P> consumer);

    ResultStream<Void> thenAcceptAsync(@NotNull ResultConsumer<P> consumer);

}

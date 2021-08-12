package cn.ideabuffer.process.core.nodes.transmitter;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * 结果处理流
 *
 * @author sangjian.sj
 * @date 2020/03/08
 */
public interface ResultStream<P> {

    /**
     * 增加后继结果处理节点
     *
     * @param processor 结果处理器
     * @param <R>       返回值类型
     * @return
     */
    <R> ResultStream<R> thenApply(@NotNull ResultProcessor<R, ? extends P> processor);

    /**
     * 增加后继结果处理节点，异步执行
     *
     * @param processor 结果处理器
     * @param <R>       返回值类型
     * @return
     */
    <R> ResultStream<R> thenApplyAsync(@NotNull ResultProcessor<R, ? extends P> processor);

    /**
     * 增加后继结果处理节点
     *
     * @param consumer 结果处理器
     * @return
     */
    ResultStream<Void> thenAccept(@NotNull ResultConsumer<? extends P> consumer);

    /**
     * 增加后继结果处理节点，异步执行
     *
     * @param consumer 结果处理器
     * @return
     */
    ResultStream<Void> thenAcceptAsync(@NotNull ResultConsumer<? extends P> consumer);

    ResultStream<P> exceptionally(Function<Throwable, ? extends P> fn);

}

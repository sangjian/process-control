package cn.ideabuffer.process.nodes.aggregate;

/**
 * @author sangjian.sj
 * @date 2020/03/08
 */
public interface ResultPostProcessor<T> {

    <R> ResultPostProcessor<R> thenApply(ResultProcessor<R, T> processor);

    ResultPostProcessor<Void> thenAccept(ResultConsumer<T> consumer);

}

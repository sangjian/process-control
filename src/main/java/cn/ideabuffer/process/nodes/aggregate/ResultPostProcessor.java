package cn.ideabuffer.process.nodes.aggregate;

/**
 * @author sangjian.sj
 * @date 2020/03/08
 */
public interface ResultPostProcessor<P> {

    <R> ResultPostProcessor<R> thenApply(ResultProcessor<R, P> processor);

    ResultPostProcessor<Void> thenAccept(ResultConsumer<P> consumer);

}

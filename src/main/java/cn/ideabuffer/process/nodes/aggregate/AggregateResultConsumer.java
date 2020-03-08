package cn.ideabuffer.process.nodes.aggregate;

/**
 * @author sangjian.sj
 * @date 2020/03/08
 */
public interface AggregateResultConsumer<T> {

    void accept(T result);

}

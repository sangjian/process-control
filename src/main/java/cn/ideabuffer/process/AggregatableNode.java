package cn.ideabuffer.process;

import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface AggregatableNode<T> extends Node, Aggregatable<T>, Parallelizable {

    AggregatableNode<T> merge(MergeableNode<T>... nodes);

    AggregatableNode<T> by(Merger<T> merger);

    void process(Context context, T result);

    @Override
    AggregatableNode<T> parallel();

    @Override
    AggregatableNode<T> parallel(Executor executor);
}

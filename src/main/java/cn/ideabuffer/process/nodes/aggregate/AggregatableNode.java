package cn.ideabuffer.process.nodes.aggregate;

import cn.ideabuffer.process.*;
import cn.ideabuffer.process.handler.ExceptionHandler;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface AggregatableNode<T> extends Node, Aggregatable, AggregatePostProcessor<T>, Parallelizable {

    AggregatableNode<T> merge(MergeableNode<T>... nodes);

    AggregatableNode<T> by(Merger<T> merger);

    @Override
    AggregatableNode<T> parallel();

    @Override
    AggregatableNode<T> parallel(Executor executor);

    AggregatableNode<T> aggregator(@NotNull Aggregator aggregator);

    Aggregator getAggregator();

    AggregatableNode<T> exceptionHandler(ExceptionHandler handler);

    ExceptionHandler getExceptionHandler();
}

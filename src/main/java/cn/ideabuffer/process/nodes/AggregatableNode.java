package cn.ideabuffer.process.nodes;

import cn.ideabuffer.process.Aggregatable;
import cn.ideabuffer.process.Node;
import cn.ideabuffer.process.Parallelizable;
import cn.ideabuffer.process.handler.ExceptionHandler;
import cn.ideabuffer.process.nodes.aggregate.ResultPostProcessor;
import cn.ideabuffer.process.nodes.aggregate.Aggregator;
import cn.ideabuffer.process.nodes.merger.Merger;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface AggregatableNode<T> extends Node, Aggregatable, ResultPostProcessor<T>, Parallelizable {

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

package cn.ideabuffer.process.nodes;

import cn.ideabuffer.process.handler.ExceptionHandler;
import cn.ideabuffer.process.nodes.transmitter.ResultStream;
import cn.ideabuffer.process.rule.Rule;

import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/03/10
 */
public interface TransmittableNode<R> extends ExecutableNode, ResultStream<R> {
    @Override
    ExecutableNode parallel();

    @Override
    ExecutableNode parallel(Executor executor);

    @Override
    TransmittableNode<R> exceptionHandler(ExceptionHandler handler);

    @Override
    TransmittableNode<R> processOn(Rule rule);

}

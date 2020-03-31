package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.handler.ExceptionHandler;
import cn.ideabuffer.process.core.nodes.transmitter.ResultStream;
import cn.ideabuffer.process.core.rule.Rule;

import java.util.concurrent.Executor;

/**
 * 可传递结果的节点
 *
 * @author sangjian.sj
 * @date 2020/03/10
 */
public interface TransmittableNode<R> extends ExecutableNode, ResultStream<R> {

    @Override
    TransmittableNode<R> parallel();

    @Override
    TransmittableNode<R> parallel(Executor executor);

    @Override
    TransmittableNode<R> exceptionHandler(ExceptionHandler handler);

    @Override
    TransmittableNode<R> processOn(Rule rule);

}

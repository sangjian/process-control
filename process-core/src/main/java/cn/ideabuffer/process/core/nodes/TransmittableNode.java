package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.nodes.transmitter.ResultStream;

/**
 * 可传递结果的节点
 *
 * @author sangjian.sj
 * @date 2020/03/10
 */
public interface TransmittableNode<R> extends ExecutableNode, ResultStream<R> {

}

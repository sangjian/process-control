package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.nodes.transmitter.ResultStream;

/**
 * 可传递结果的节点
 *
 * @author sangjian.sj
 * @date 2020/03/10
 */
public interface TransmittableNode<R, P extends Processor<R>> extends ExecutableNode<R, P>, ResultStream<R> {

}

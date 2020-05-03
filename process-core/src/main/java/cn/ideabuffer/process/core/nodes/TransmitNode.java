package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.nodes.transmitter.AbstractTransmittableNode;

/**
 * @author sangjian.sj
 * @date 2020/05/03
 */
public class TransmitNode<R> extends AbstractTransmittableNode<R, Processor<R>> {

    public TransmitNode() {
        super();
    }

    public TransmitNode(Processor<R> processor) {
        super.registerProcessor(processor);
    }

}

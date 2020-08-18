package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.nodes.transmitter.AbstractTransmittableNode;

/**
 * @author sangjian.sj
 * @date 2020/05/03
 */
public class TransmissionNode<R> extends AbstractTransmittableNode<R, Processor<R>> {

    public TransmissionNode() {
        super();
    }

    public TransmissionNode(Processor<R> processor) {
        super.registerProcessor(processor);
    }

}

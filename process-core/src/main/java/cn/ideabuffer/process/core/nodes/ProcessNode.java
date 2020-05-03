package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.Processor;

/**
 * @author sangjian.sj
 * @date 2020/05/03
 */
public class ProcessNode<R> extends AbstractExecutableNode<R, Processor<R>> {
    public ProcessNode() {
        super();
    }

    public ProcessNode(Processor<R> processor) {
        super.registerProcessor(processor);
    }

}

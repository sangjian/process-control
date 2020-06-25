package cn.ideabuffer.process.api.model.builder;

import cn.ideabuffer.process.api.model.listener.NodeListenerModel;
import cn.ideabuffer.process.api.model.processor.ProcessorModel;
import cn.ideabuffer.process.core.NodeListener;
import cn.ideabuffer.process.core.Processor;

/**
 * @author sangjian.sj
 * @date 2020/06/23
 */
public class NodeListenerModelBuilder<R extends NodeListener> extends ModelBuilder<R> {

    @Override
    public NodeListenerModel<R> build(R resource) {
        return new NodeListenerModel<>(resource);
    }
}

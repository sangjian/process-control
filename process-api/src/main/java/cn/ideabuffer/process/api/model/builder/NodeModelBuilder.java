package cn.ideabuffer.process.api.model.builder;

import cn.ideabuffer.process.api.model.Model;
import cn.ideabuffer.process.api.model.node.NodeModel;
import cn.ideabuffer.process.core.Node;

/**
 * @author sangjian.sj
 * @date 2020/06/23
 */
public class NodeModelBuilder<R extends Node> extends ModelBuilder<R> {

    @Override
    public NodeModel build(R resource) {
        return new NodeModel<>(resource);
    }
}

package cn.ideabuffer.process.api.model.builder;

import cn.ideabuffer.process.api.model.node.ExecutableNodeModel;
import cn.ideabuffer.process.api.model.node.NodeModel;
import cn.ideabuffer.process.core.Node;
import cn.ideabuffer.process.core.nodes.ExecutableNode;

/**
 * @author sangjian.sj
 * @date 2020/06/23
 */
public class ExecutableNodeModelBuilder<R extends ExecutableNode> extends NodeModelBuilder<R> {

    @Override
    public ExecutableNodeModel<R> build(R resource) {
        return new ExecutableNodeModel<>(resource);
    }
}

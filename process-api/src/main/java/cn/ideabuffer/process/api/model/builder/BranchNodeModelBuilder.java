package cn.ideabuffer.process.api.model.builder;

import cn.ideabuffer.process.api.model.node.BranchNodeModel;
import cn.ideabuffer.process.api.model.node.ExecutableNodeModel;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;

/**
 * @author sangjian.sj
 * @date 2020/06/23
 */
public class BranchNodeModelBuilder<R extends BranchNode> extends NodeModelBuilder<R> {

    @Override
    public BranchNodeModel<R> build(R resource) {
        return new BranchNodeModel<>(resource);
    }
}

package cn.ideabuffer.process.api.model.builder;

import cn.ideabuffer.process.api.model.node.IfConditionNodeModel;
import cn.ideabuffer.process.core.nodes.condition.IfConditionNode;

/**
 * @author sangjian.sj
 * @date 2020/06/23
 */
public class IfConditionNodeModelBuilder<R extends IfConditionNode> extends ExecutableNodeModelBuilder<R> {

    @Override
    public IfConditionNodeModel<R> build(R resource) {
        return new IfConditionNodeModel<>(resource);
    }
}

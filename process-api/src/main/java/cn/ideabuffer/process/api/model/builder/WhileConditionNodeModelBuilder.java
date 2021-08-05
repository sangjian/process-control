package cn.ideabuffer.process.api.model.builder;

import cn.ideabuffer.process.api.model.node.WhileConditionNodeModel;
import cn.ideabuffer.process.core.nodes.condition.WhileConditionNode;

/**
 * @author sangjian.sj
 * @date 2020/06/23
 */
public class WhileConditionNodeModelBuilder<R extends WhileConditionNode> extends ExecutableNodeModelBuilder<R> {

    @Override
    public WhileConditionNodeModel<R> build(R resource) {
        return new WhileConditionNodeModel<>(resource);
    }
}

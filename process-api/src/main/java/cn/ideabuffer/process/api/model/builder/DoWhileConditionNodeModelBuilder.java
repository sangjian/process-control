package cn.ideabuffer.process.api.model.builder;

import cn.ideabuffer.process.api.model.node.DoWhileConditionNodeModel;
import cn.ideabuffer.process.api.model.node.WhileConditionNodeModel;
import cn.ideabuffer.process.core.nodes.condition.DoWhileConditionNode;
import cn.ideabuffer.process.core.nodes.condition.WhileConditionNode;

/**
 * @author sangjian.sj
 * @date 2020/06/23
 */
public class DoWhileConditionNodeModelBuilder<R extends DoWhileConditionNode> extends ExecutableNodeModelBuilder<R> {

    @Override
    public DoWhileConditionNodeModel<R> build(R resource) {
        return new DoWhileConditionNodeModel<>(resource);
    }
}

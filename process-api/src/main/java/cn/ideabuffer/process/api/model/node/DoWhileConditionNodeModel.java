package cn.ideabuffer.process.api.model.node;

import cn.ideabuffer.process.core.nodes.condition.DoWhileConditionNode;
import cn.ideabuffer.process.core.nodes.condition.WhileConditionNode;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/06/22
 */
public class DoWhileConditionNodeModel<R extends DoWhileConditionNode> extends WhileConditionNodeModel<R> {

    private static final long serialVersionUID = 4752588656489835445L;

    public DoWhileConditionNodeModel(@NotNull R node) {
        super(node);
    }
}

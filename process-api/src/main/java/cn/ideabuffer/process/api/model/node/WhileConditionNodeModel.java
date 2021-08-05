package cn.ideabuffer.process.api.model.node;

import cn.ideabuffer.process.core.nodes.condition.WhileConditionNode;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/06/22
 */
public class WhileConditionNodeModel<R extends WhileConditionNode> extends ExecutableNodeModel<R> {

    private static final long serialVersionUID = 6443601990446120760L;

    public WhileConditionNodeModel(@NotNull R node) {
        super(node);
    }

}

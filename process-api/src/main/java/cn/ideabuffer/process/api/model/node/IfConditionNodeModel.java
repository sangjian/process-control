package cn.ideabuffer.process.api.model.node;

import cn.ideabuffer.process.core.nodes.condition.IfConditionNode;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/06/22
 */
public class IfConditionNodeModel<R extends IfConditionNode> extends ExecutableNodeModel<R> {

    private static final long serialVersionUID = -1731777331107190758L;

    public IfConditionNodeModel(@NotNull R node) {
        super(node);
    }
}

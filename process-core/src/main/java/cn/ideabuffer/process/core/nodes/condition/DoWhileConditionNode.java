package cn.ideabuffer.process.core.nodes.condition;

import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.processors.DefaultDoWhileProcessor;
import cn.ideabuffer.process.core.processors.WhileProcessor;
import cn.ideabuffer.process.core.rule.Rule;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public class DoWhileConditionNode extends WhileConditionNode {

    public DoWhileConditionNode(@NotNull Rule rule, @NotNull BranchNode branch) {
        super(new DefaultDoWhileProcessor(rule, branch));
    }

}

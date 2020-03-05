package cn.ideabuffer.process.condition;

import cn.ideabuffer.process.ExecutableNode;
import cn.ideabuffer.process.branch.Branch;
import cn.ideabuffer.process.branch.DefaultBranch;
import cn.ideabuffer.process.rule.Rule;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class WhileWhen {

    protected Rule rule;

    public WhileWhen() {
    }

    public WhileWhen(Rule rule) {
        this.rule = rule;
    }

    public WhileWhen when(Rule rule) {
        this.rule = rule;
        return this;
    }

    public WhileConditionNode then(Branch branch) {
        return new WhileConditionNode(rule, branch);
    }

    public WhileConditionNode then(ExecutableNode... nodes) {
        return then(new DefaultBranch(nodes));
    }

}

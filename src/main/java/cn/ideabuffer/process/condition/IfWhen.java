package cn.ideabuffer.process.condition;

import cn.ideabuffer.process.ExecutableNode;
import cn.ideabuffer.process.branch.Branch;
import cn.ideabuffer.process.branch.DefaultBranch;
import cn.ideabuffer.process.rule.Rule;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class IfWhen {

    private Rule rule;

    private Branch trueBranch;

    private Branch falseBranch;

    public IfWhen when(Rule rule) {
        this.rule = rule;
        return this;
    }

    public IfWhen then(Branch branch) {
        trueBranch = branch;
        return this;
    }

    public IfWhen then(ExecutableNode... nodes) {
        return then(new DefaultBranch(nodes));
    }

    public IfConditionNode otherwise(Branch branch) {
        falseBranch = branch;
        return end();
    }

    public IfConditionNode otherwise(ExecutableNode... nodes) {
        return otherwise(new DefaultBranch(nodes));
    }

    public IfConditionNode end() {
        return new IfConditionNode(rule, trueBranch, falseBranch);
    }

}

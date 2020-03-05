package cn.ideabuffer.process.condition;

import cn.ideabuffer.process.branch.Branch;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class DoWhileWhen extends WhileWhen {

    @Override
    public WhileConditionNode then(Branch branch) {
        return new DoWhileConditionNode(rule, branch);
    }

}

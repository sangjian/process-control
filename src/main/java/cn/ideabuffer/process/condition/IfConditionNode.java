package cn.ideabuffer.process.condition;

import cn.ideabuffer.process.branch.Branch;
import cn.ideabuffer.process.branch.BranchNode;
import cn.ideabuffer.process.ExecutableNode;

/**
 * @author sangjian.sj
 * @date 2020/01/20
 */
public interface IfConditionNode extends BranchNode<Boolean> {

    /**
     * 添加true分支节点
     * @param branch
     * @return
     */
    IfConditionNode trueBranch(Branch branch);

    /**
     * 添加false分支节点
     * @param branch
     * @return
     */
    IfConditionNode falseBranch(Branch branch);

    /**
     * 添加true分支节点
     * @param nodes
     * @return
     */
    IfConditionNode trueBranch(ExecutableNode... nodes);

    /**
     * 添加false分支节点
     * @param nodes
     * @return
     */
    IfConditionNode falseBranch(ExecutableNode... nodes);

    /**
     * 获取true分支节点
     * @return
     */
    Branch getTrueBranch();

    /**
     * 获取false分支节点
     * @return
     */
    Branch getFalseBranch();

}

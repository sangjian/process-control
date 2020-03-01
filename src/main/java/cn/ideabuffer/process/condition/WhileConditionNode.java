package cn.ideabuffer.process.condition;

import cn.ideabuffer.process.branch.Branch;
import cn.ideabuffer.process.branch.BranchNode;
import cn.ideabuffer.process.ExecutableNode;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/01/20
 */
public interface WhileConditionNode extends BranchNode<Boolean> {

    /**
     * 增加while分支节点
     * @param nodes 可执行节点
     * @return
     */
    WhileConditionNode addNode(ExecutableNode... nodes);

    /**
     * 获取while分支节点列表
     * @return
     */
    Branch getBranch();

}

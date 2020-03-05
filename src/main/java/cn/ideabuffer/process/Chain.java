package cn.ideabuffer.process;

import cn.ideabuffer.process.branch.BranchNode;
import cn.ideabuffer.process.condition.DoWhileConditionNode;
import cn.ideabuffer.process.condition.IfConditionNode;
import cn.ideabuffer.process.condition.WhileConditionNode;
import cn.ideabuffer.process.nodes.NodeGroup;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public interface Chain extends ExecutableNode {

    /**
     * 增加执行节点
     * @param node 可执行节点
     * @return
     */
    Chain addProcessNode(ExecutableNode node);

    /**
     * 增加if节点
     * @param node 条件节点
     * @return
     */
    Chain addIf(IfConditionNode node);

    /**
     * 增加while节点
     * @param node 条件节点
     * @return
     */
    Chain addWhile(WhileConditionNode node);

    /**
     * 增加dowhile节点
     * @param node 条件节点
     * @return
     */
    Chain addDoWhile(DoWhileConditionNode node);

    /**
     * 增加节点组
     * @param group 节点组
     * @return
     */
    Chain addGroup(NodeGroup group);

}

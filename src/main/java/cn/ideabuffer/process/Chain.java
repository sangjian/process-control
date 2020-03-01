package cn.ideabuffer.process;

import cn.ideabuffer.process.branch.BranchNode;
import cn.ideabuffer.process.nodes.ExecutableNodeGroup;

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
     * 增加条件节点
     * @param node 条件节点
     * @return
     */
    Chain addConditionNode(BranchNode node);

    /**
     * 增加节点组
     * @param group 节点组
     * @return
     */
    Chain addNodeGroup(ExecutableNodeGroup group);

}

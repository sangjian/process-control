package cn.ideabuffer.process;

import cn.ideabuffer.process.condition.ConditionNode;
import cn.ideabuffer.process.group.ExecutableNodeGroup;

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
    Chain addConditionNode(ConditionNode node);

    /**
     * 增加节点组
     * @param group 节点组
     * @return
     */
    Chain addNodeGroup(ExecutableNodeGroup group);

}

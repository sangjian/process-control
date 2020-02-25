package cn.ideabuffer.process;

import cn.ideabuffer.process.condition.ConditionNode;
import cn.ideabuffer.process.group.ExecutableNodeGroup;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public interface Chain extends ExecutableNode {

    Chain addProcessNode(ExecutableNode node);

    Chain addConditionNode(ConditionNode node);

    Chain addNodeGroup(ExecutableNodeGroup group);

}

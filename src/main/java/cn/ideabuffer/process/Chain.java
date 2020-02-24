package cn.ideabuffer.process;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public interface Chain extends ExecutableNode {

    Chain addProcessNode(AbstractExecutableNode node);

    Chain addConditionNode(ConditionNode node);

    Chain addNodeGroup(ExecutableNodeGroup group);

}

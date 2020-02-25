package cn.ideabuffer.process.condition;

import cn.ideabuffer.process.ExecutableNode;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/01/20
 */
public interface WhileConditionNode extends ConditionNode<Boolean> {

    WhileConditionNode addNode(ExecutableNode node);

    List<ExecutableNode> getNodes();

}

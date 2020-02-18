package cn.ideabuffer.process;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/01/20
 */
public interface IfConditionNode<E> extends ConditionNode<Boolean> {

    IfConditionNode<E> addTrueNode(ExecutableNode node);

    IfConditionNode<E> addFalseNode(ExecutableNode node);

    List<ExecutableNode> getTrueNodes();

    List<ExecutableNode> getFalseNodes();

}

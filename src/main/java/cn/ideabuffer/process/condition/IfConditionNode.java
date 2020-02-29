package cn.ideabuffer.process.condition;

import cn.ideabuffer.process.ExecutableNode;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/01/20
 */
public interface IfConditionNode<E> extends ConditionNode<Boolean> {

    /**
     * 添加true分支节点
     * @param node
     * @return
     */
    IfConditionNode<E> addTrueNode(ExecutableNode node);

    /**
     * 添加false分支节点
     * @param node
     * @return
     */
    IfConditionNode<E> addFalseNode(ExecutableNode node);

    /**
     * 获取true分支节点
     * @return
     */
    List<ExecutableNode> getTrueNodes();

    /**
     * 获取false分支节点
     * @return
     */
    List<ExecutableNode> getFalseNodes();

}

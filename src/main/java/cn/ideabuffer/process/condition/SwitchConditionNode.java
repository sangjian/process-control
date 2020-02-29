package cn.ideabuffer.process.condition;

import cn.ideabuffer.process.ExecutableNode;
import cn.ideabuffer.process.ExpectableNode;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/01/20
 */
public interface SwitchConditionNode<V> extends ConditionNode<V> {

    /**
     * 增加case节点
     * @param node
     * @return
     */
    SwitchConditionNode<V> switchCase(ExpectableNode<V> node);

    /**
     * 增加default节点
     * @param node
     * @return
     */
    SwitchConditionNode<V> defaultCase(ExecutableNode node);

    /**
     * 获取case节点列表
     * @return
     */
    List<ExpectableNode<V>> getCaseNodes();

    /**
     * 获取default节点列表
     * @return
     */
    List<ExecutableNode> getDefaultNodes();

}

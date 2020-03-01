package cn.ideabuffer.process.condition;

import cn.ideabuffer.process.branch.BranchNode;
import cn.ideabuffer.process.ExecutableNode;
import cn.ideabuffer.process.ExpectableNode;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/01/20
 */
public interface SwitchConditionNode<V> extends BranchNode<V> {

    /**
     * 增加case节点
     * @param nodes
     * @return
     */
    SwitchConditionNode<V> switchCase(ExpectableNode<V>... nodes);

    /**
     * 增加default节点
     * @param nodes
     * @return
     */
    SwitchConditionNode<V> defaultCase(ExecutableNode... nodes);

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

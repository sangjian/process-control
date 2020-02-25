package cn.ideabuffer.process.condition;

import cn.ideabuffer.process.ExecutableNode;
import cn.ideabuffer.process.ExpectableNode;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/01/20
 */
public interface SwitchConditionNode<V> extends ConditionNode<V> {

    SwitchConditionNode<V> switchCase(ExpectableNode<V> node);

    SwitchConditionNode<V> defaultCase(ExecutableNode node);

    List<ExpectableNode<V>> getCaseNodes();

    ExecutableNode getDefaultNode();

}

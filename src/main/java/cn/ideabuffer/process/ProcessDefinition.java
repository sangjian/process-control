package cn.ideabuffer.process;

import cn.ideabuffer.process.nodes.AggregatableNode;
import cn.ideabuffer.process.nodes.BaseNode;
import cn.ideabuffer.process.nodes.ExecutableNode;
import cn.ideabuffer.process.nodes.NodeGroup;
import cn.ideabuffer.process.nodes.branch.BranchNode;
import cn.ideabuffer.process.nodes.condition.DoWhileConditionNode;
import cn.ideabuffer.process.nodes.condition.IfConditionNode;
import cn.ideabuffer.process.nodes.condition.WhileConditionNode;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/03/24
 */
public interface ProcessDefinition<R> {

    /**
     * 增加执行节点
     *
     * @param node 可执行节点
     * @return 当前实例
     */
    ProcessDefinition<R> addProcessNode(@NotNull ExecutableNode... nodes);

    /**
     * 增加if节点
     *
     * @param node 条件节点
     * @return 当前实例
     */
    ProcessDefinition<R> addIf(@NotNull IfConditionNode node);

    /**
     * 增加while节点
     *
     * @param node 条件节点
     * @return 当前实例
     */
    ProcessDefinition<R> addWhile(@NotNull WhileConditionNode node);

    /**
     * 增加dowhile节点
     *
     * @param node 条件节点
     * @return 当前实例
     */
    ProcessDefinition<R> addDoWhile(@NotNull DoWhileConditionNode node);

    /**
     * 增加节点组
     *
     * @param group 节点组
     * @return 当前实例
     */
    ProcessDefinition<R> addGroup(@NotNull NodeGroup group);

    /**
     * 增加聚合节点
     *
     * @param node 聚合节点
     * @return 当前实例
     */
    ProcessDefinition<R> addAggregateNode(@NotNull AggregatableNode node);

    /**
     * 增加分支节点
     *
     * @param node 分支节点
     * @return
     */
    ProcessDefinition<R> addBranchNode(@NotNull BranchNode node);

    /**
     * 增加基础节点
     *
     * @param node
     * @return
     * @see BaseNode
     */
    ProcessDefinition<R> addBaseNode(@NotNull BaseNode<R> node);

    /**
     * 获取所有节点
     *
     * @return
     */
    @NotNull
    Node[] getNodes();

    /**
     * 获取基础节点
     *
     * @return
     */
    BaseNode<R> getBaseNode();
}
package cn.ideabuffer.process.core;

import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.nodes.NodeGroup;
import cn.ideabuffer.process.core.nodes.aggregate.AggregatableNode;
import cn.ideabuffer.process.core.nodes.aggregate.DistributeAggregatableNode;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.nodes.condition.DoWhileConditionNode;
import cn.ideabuffer.process.core.nodes.condition.IfConditionNode;
import cn.ideabuffer.process.core.nodes.condition.WhileConditionNode;
import cn.ideabuffer.process.core.processors.wrapper.StatusWrapperHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/03/24
 */
public interface ProcessDefinition<R> extends Lifecycle {

    /**
     * 增加执行节点
     *
     * @param node 可执行节点
     * @return 当前实例
     */
    ProcessDefinition<R> addProcessNode(@NotNull ExecutableNode<?, ?> node);

    /**
     * 增加执行节点
     *
     * @param nodes 可执行节点
     * @return 当前实例
     */
    ProcessDefinition<R> addProcessNodes(@NotNull ExecutableNode<?, ?>... nodes);

    /**
     * 增加执行节点
     *
     * @param nodes 可执行节点
     * @return 当前实例
     */
    ProcessDefinition<R> addProcessNodes(@NotNull List<ExecutableNode<?, ?>> nodes);

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
    <I, O> ProcessDefinition<R> addAggregateNode(@NotNull AggregatableNode<I, O> node);

    /**
     * 增加聚合节点
     *
     * @param node 聚合节点
     * @return 当前实例
     */
    <O> ProcessDefinition<R> addDistributeAggregateNode(@NotNull DistributeAggregatableNode<O> node);

    /**
     * 增加分支节点
     *
     * @param node 分支节点
     * @return
     */
    ProcessDefinition<R> addBranchNode(@NotNull BranchNode node);

    /**
     * 获取所有节点
     *
     * @return
     */
    @NotNull
    Node[] getNodes();

    InitializeMode getInitializeMode();

    @NotNull
    ProcessInstance<R> newInstance();

    ProcessDefinition<R> resultKey(@NotNull Key<R> key);

    @Nullable
    Key<R> getResultKey();

    void returnOn(ReturnCondition<R> condition);

    ReturnCondition<R> getReturnCondition();

    /**
     * 注册包装处理器
     *
     * @param handlers 包装处理器
     * @return 当前流程定义
     */
    ProcessDefinition<R> wrap(@NotNull StatusWrapperHandler... handlers);

    /**
     * 返回包装处理器
     *
     * @return 包装处理器列表
     * @see java.util.Collections#unmodifiableList(List)
     */
    @NotNull
    List<StatusWrapperHandler> getHandlers();
}

package cn.ideabuffer.process.core.processors;

import cn.ideabuffer.process.core.aggregator.GenericAggregator;
import cn.ideabuffer.process.core.nodes.GenericMergeableNode;
import cn.ideabuffer.process.core.nodes.MergeableNode;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 聚合处理器，用于聚合 {@link MergeableNode} 中处理器的返回结果。
 *
 * @param <I> 输入类型
 * @param <O> 输出类型
 * @author sangjian.sj
 * @date 2020/05/02
 */
public interface AggregateProcessor<I, O> extends ComplexProcessor<O> {

    /**
     * 添加合并节点
     *
     * @param nodes 合并节点列表
     */
    void aggregate(@NotNull List<GenericMergeableNode<I>> nodes);

    /**
     * 设置聚合器
     *
     * @param aggregator 聚合器
     */
    void aggregator(@NotNull GenericAggregator<I, O> aggregator);

    /**
     * 获取聚合器
     *
     * @return 聚合器
     */
    GenericAggregator<I, O> getAggregator();

    /**
     * 获取合并节点
     *
     * @return 合并节点列表
     */
    List<GenericMergeableNode<I>> getMergeableNodes();

}

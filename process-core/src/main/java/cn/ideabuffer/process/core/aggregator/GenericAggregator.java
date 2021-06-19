package cn.ideabuffer.process.core.aggregator;

import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.GenericMergeableNode;
import cn.ideabuffer.process.core.nodes.merger.Merger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 通用聚合器，用于合并{@link GenericMergeableNode}节点的{@link Processor}返回结果，每个{@link GenericMergeableNode}中的{@link
 * Processor}节点返回结果类型相同。在聚合时通过指定的{@link Merger#merge}进行具体聚合。
 *
 * <pre>
 *    ┌————————————————————┐               ┌————————————————————┐            ┌————————————————————┐
 *    |   MergeableNodeA   |               |   MergeableNodeB   |            |   MergeableNodeC   |
 *    └————————————————————┘               └————————————————————┘            └————————————————————┘
 *               |                                   |                                  |
 *               |                                   |                                  |
 *    ┌—————————————————————————┐       ┌—————————————————————————┐       ┌—————————————————————————┐
 *    |   NodeResultA(type:I)   |       |   NodeResultB(type:I)   |       |   NodeResultC(type:I)   |
 *    └—————————————————————————┘       └—————————————————————————┘       └—————————————————————————┘
 *               |                                   |                                  |
 *               |                                   |                                  |
 *               |___________________________________|__________________________________|
 *                                                   |
 *                                                   |
 *                                          ┌—————————————————┐
 *                                          |    Aggregator   |
 *                                          └—————————————————┘
 *                                                   |
 *                                                   |
 *                                        ┌——————————————————————┐
 *                                        |    result(type:O)    |
 *                                        └——————————————————————┘
 * </pre>
 *
 * @param <I> 输入类型
 * @param <O> 输出类型，聚合后的结果类型
 * @author sangjian.sj
 * @date 2020/03/28
 */
public interface GenericAggregator<I, O> extends Aggregator<O, GenericMergeableNode<I>> {

    /**
     * {@inheritDoc}
     *
     * @param context 流程上下文
     * @param nodes   可合并节点
     * @return 聚合结果
     * @throws Exception
     */
    @Override
    @Nullable
    O aggregate(@NotNull Context context, List<GenericMergeableNode<I>> nodes) throws Exception;

}

package cn.ideabuffer.process.core.aggregator;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.GenericMergeableNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
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
 * @author sangjian.sj
 * @date 2020/03/28
 */
public interface GenericAggregator<I, O> extends Aggregator<O, GenericMergeableNode<I>> {

    /**
     * 对可聚合的节点进行结果聚合，并返回聚合后的结果
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

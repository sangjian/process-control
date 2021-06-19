package cn.ideabuffer.process.core.aggregator;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.DistributeMergeableNode;
import cn.ideabuffer.process.core.processors.DistributeProcessor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 分布式聚合器，用于合并{@link DistributeMergeableNode}节点的返回结果，每个{@link DistributeMergeableNode}节点结果类型不同，在聚合时会调用{@link DistributeProcessor#merge}进行具体聚合。
 *
 * <pre>
 *    ┌——————————————————————————————┐               ┌——————————————————————————————┐            ┌——————————————————————————————┐
 *    |   DistributeMergeableNodeA   |               |   DistributeMergeableNodeB   |            |   DistributeMergeableNodeC   |
 *    └——————————————————————————————┘               └——————————————————————————————┘            └——————————————————————————————┘
 *                   |                                              |                                            |
 *                   |                                              |                                            |
 *      ┌—————————————————————————┐                    ┌—————————————————————————┐                  ┌—————————————————————————┐
 *      |   NodeResultA(type:A)   |                    |   NodeResultB(type:B)   |                  |   NodeResultC(type:C)   |
 *      └—————————————————————————┘                    └—————————————————————————┘                  └—————————————————————————┘
 *                   |                                              |                                            |
 *                   |                                              |                                            |
 *                   |______________________________________________|____________________________________________|
 *                                                                  |
 *                                                                  |
 *                                                    ┌———————————————————————————┐
 *                                                    |    DistributeAggregator   |
 *                                                    └———————————————————————————┘
 *                                                                  |
 *                                                                  |
 *                                                       ┌——————————————————————┐
 *                                                       |    result(type:O)    |
 *                                                       └——————————————————————┘
 * </pre>
 *
 * @param <R> 聚合结果类型
 * @author sangjian.sj
 * @date 2020/03/27
 * @see DistributeMergeableNode
 * @see DistributeProcessor
 */
public interface DistributeAggregator<R> extends Aggregator<R, DistributeMergeableNode<?, R>> {

    /**
     * 对可聚合的节点进行结果聚合，并返回聚合后的结果
     *
     * @param context 流程上下文
     * @param nodes   可合并节点
     * @return 聚合结果
     * @throws Exception
     */
    @Override
    @NotNull
    R aggregate(@NotNull Context context, List<DistributeMergeableNode<?, R>> nodes) throws Exception;
}

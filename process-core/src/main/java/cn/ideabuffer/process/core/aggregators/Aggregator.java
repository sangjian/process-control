package cn.ideabuffer.process.core.aggregators;

import cn.ideabuffer.process.core.Lifecycle;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.MergeableNode;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 聚合器，聚合一组 {@link MergeableNode} ，并返回指定类型的结果。
 *
 * @param <R> 聚合结果类型
 * @param <N> 可合并节点{@link MergeableNode}
 * @author sangjian.sj
 * @date 2020/06/06
 */
public interface Aggregator<R, N extends MergeableNode> extends Lifecycle {

    /**
     * 对可聚合的节点进行结果聚合，并返回聚合后的结果
     *
     * @param context 流程上下文
     * @param nodes   可合并结果节点
     * @return 聚合结果
     * @throws Exception
     * @see MergeableNode
     */
    R aggregate(@NotNull Context context, List<N> nodes) throws Exception;

}

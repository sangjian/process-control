package cn.ideabuffer.process.core.aggregator;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.MergeableNode;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 聚合器，聚合一组 {@link MergeableNode} ，并返回指定类型的结果
 *
 * @param <R> 聚合结果类型
 * @param <N> {@link MergeableNode}
 * @author sangjian.sj
 * @date 2020/06/06
 */
public interface Aggregator<R, N extends MergeableNode> {

    R aggregate(@NotNull Context context, List<N> nodes) throws Exception;

}

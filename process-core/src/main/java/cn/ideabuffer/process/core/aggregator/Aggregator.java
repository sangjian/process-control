package cn.ideabuffer.process.core.aggregator;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.MergeableNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 聚合器
 *
 * @param <I> 可合并节点返回类型
 * @param <O> 聚合结果类型
 * @author sangjian.sj
 * @date 2020/03/08
 */
public interface Aggregator<I, O> {

    /**
     * 对可聚合的节点进行结果聚合，并返回聚合后的结果
     *
     * @param context 流程上下文
     * @param nodes   可合并节点
     * @return 聚合结果
     * @throws Exception
     */
    @Nullable
    O aggregate(@NotNull Context context, List<MergeableNode<I>> nodes) throws Exception;

}

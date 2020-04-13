package cn.ideabuffer.process.core.aggregator;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.MergeableNode;

import java.util.List;

/**
 * 聚合器
 *
 * @param <M> 可合并节点列表
 * @param <R> 聚合结果类型
 * @author sangjian.sj
 * @date 2020/03/08
 */
public interface Aggregator<M extends List<? extends MergeableNode<?>>, R> {

    /**
     * 对可聚合的节点进行结果聚合，并返回聚合后的结果
     *
     * @param context 流程上下文
     * @param nodes   可合并节点
     * @return 聚合结果
     * @throws Exception
     */
    R aggregate(Context context, M nodes) throws Exception;

}

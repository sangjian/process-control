package cn.ideabuffer.process.nodes.aggregate;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.nodes.MergeableNode;
import cn.ideabuffer.process.nodes.merger.Merger;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * 聚合器
 *
 * @author sangjian.sj
 * @date 2020/03/08
 */
public interface Aggregator {

    /**
     * 对可聚合的节点进行结果聚合，并返回聚合后的结果
     *
     * @param executor 执行器
     * @param merger
     * @param context
     * @param nodes
     * @param <T>
     * @return
     * @throws Exception
     */
    <T> T aggregate(Executor executor, Merger<T> merger, Context context, List<MergeableNode<T>> nodes)
        throws Exception;

}

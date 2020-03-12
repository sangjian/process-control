package cn.ideabuffer.process.nodes.aggregate;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.nodes.MergeableNode;

import java.util.List;

/**
 * 聚合器
 *
 * @author sangjian.sj
 * @date 2020/03/08
 */
public interface Aggregator<R> {

    /**
     * 对可聚合的节点进行结果聚合，并返回聚合后的结果
     *
     * @param context
     * @param nodes
     * @return
     * @throws Exception
     */
    R aggregate(Context context, List<MergeableNode<R>> nodes) throws Exception;

}

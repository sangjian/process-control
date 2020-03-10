package cn.ideabuffer.process.nodes.aggregate;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.nodes.MergeableNode;
import cn.ideabuffer.process.nodes.merger.Merger;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/03/08
 */
public interface Aggregator {

    <T> T aggregate(Executor executor, Merger<T> merger, Context context, List<MergeableNode<T>> nodes)
        throws Exception;

}

package cn.ideabuffer.process.core.aggregator;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.MergeableNode;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/06/06
 */
public interface Aggregator<R, N extends MergeableNode> {

    R aggregate(@NotNull Context context, List<N> nodes) throws Exception;

}

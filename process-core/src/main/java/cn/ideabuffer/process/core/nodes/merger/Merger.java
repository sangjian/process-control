package cn.ideabuffer.process.core.nodes.merger;

import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.nodes.MergeableNode;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * 结果合并器，在聚合节点执行时会使用该类的实例，将各个 {@link MergeableNode} 节点中的 {@link Processor} 返回的结果进行合并。
 *
 * @param <P> 需要合并的输入类型
 * @param <R> 合并结果类型
 * @author sangjian.sj
 * @date 2020/03/07
 * @see cn.ideabuffer.process.core.aggregators.Aggregator
 * @see cn.ideabuffer.process.core.aggregators.GenericAggregator
 * @see cn.ideabuffer.process.core.aggregators.UnitAggregator
 */
public interface Merger<P, R> {

    /**
     * 结果合并
     *
     * @param results 待合并的结果集合
     * @return 合并结果
     */
    R merge(@NotNull Collection<P> results);

}

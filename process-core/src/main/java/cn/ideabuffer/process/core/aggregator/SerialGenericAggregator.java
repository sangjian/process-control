package cn.ideabuffer.process.core.aggregator;

import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.MergeableNode;
import cn.ideabuffer.process.core.nodes.merger.Merger;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author sangjian.sj
 * @date 2020/03/09
 */
public class SerialGenericAggregator<I, O> implements GenericAggregator<I, O> {

    private Merger<I, O> merger;

    public SerialGenericAggregator(@NotNull Merger<I, O> merger) {
        this.merger = merger;
    }

    @Override
    public O aggregate(Context context, List<MergeableNode<I>> nodes)
        throws Exception {
        if (nodes == null || nodes.isEmpty()) {
            return null;
        }
        List<I> results = nodes.stream().map(MergeableNode::getProcessor).filter(Objects::nonNull).map(p -> {
            try {
                return p.process(context);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
        return merger.merge(results);
    }
}

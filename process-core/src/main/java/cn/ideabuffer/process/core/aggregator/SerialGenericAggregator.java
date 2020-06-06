package cn.ideabuffer.process.core.aggregator;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.GenericMergeableNode;
import cn.ideabuffer.process.core.nodes.merger.Merger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author sangjian.sj
 * @date 2020/03/09
 */
public class SerialGenericAggregator<I, O> implements GenericAggregator<I, O> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Merger<I, O> merger;

    public SerialGenericAggregator(@NotNull Merger<I, O> merger) {
        this.merger = merger;
    }

    @Nullable
    @Override
    public O aggregate(@NotNull Context context, List<GenericMergeableNode<I>> nodes) throws Exception {
        if (nodes == null || nodes.isEmpty()) {
            return null;
        }
        List<I> results = nodes.stream().map(GenericMergeableNode::getProcessor).filter(Objects::nonNull).map(p -> {
            try {
                return p.process(context);
            } catch (Exception e) {
                logger.error("process error!", e);
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
        return merger.merge(results);
    }
}

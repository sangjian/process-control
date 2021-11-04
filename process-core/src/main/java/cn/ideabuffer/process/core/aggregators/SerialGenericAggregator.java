package cn.ideabuffer.process.core.aggregators;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.GenericMergeableNode;
import cn.ideabuffer.process.core.nodes.merger.Merger;
import cn.ideabuffer.process.core.utils.AggregateUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 串行通用聚合器
 *
 * @param <I> 输入类型
 * @param <O> 输出类型
 * @author sangjian.sj
 * @date 2020/03/09
 * @see GenericAggregator
 */
public class SerialGenericAggregator<I, O> extends AbstractAggregator implements GenericAggregator<I, O> {

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
        List<I> results = nodes.stream().filter(Objects::nonNull)
            .filter(node -> node.getProcessor() != null)
            .map(node -> {
                try {
                    return AggregateUtils.process(context, node);
                } catch (Exception e) {
                    logger.error("process error!", e);
                }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
        return merger.merge(results);
    }
}

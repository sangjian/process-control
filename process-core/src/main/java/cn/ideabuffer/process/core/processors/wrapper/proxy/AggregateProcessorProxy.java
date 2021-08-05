package cn.ideabuffer.process.core.processors.wrapper.proxy;

import cn.ideabuffer.process.core.aggregators.GenericAggregator;
import cn.ideabuffer.process.core.nodes.GenericMergeableNode;
import cn.ideabuffer.process.core.processors.AggregateProcessor;
import cn.ideabuffer.process.core.processors.wrapper.WrapperHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2021/06/17
 */
public class AggregateProcessorProxy<I, O> extends AbstractProcessorProxy<AggregateProcessor<I, O>, O>
    implements AggregateProcessor<I, O> {

    public AggregateProcessorProxy(
        @NotNull AggregateProcessor<I, O> target,
        @NotNull WrapperHandler<O> handler) {
        super(target, handler);
    }

    public static <I, O> AggregateProcessor<I, O> wrap(@NotNull AggregateProcessor<I, O> target,
        List<WrapperHandler<O>> handlers) {
        if (handlers == null || handlers.isEmpty()) {
            return target;
        }
        AggregateProcessor<I, O> wrapped = target;
        for (int i = handlers.size() - 1; i >= 0; i--) {
            wrapped = new AggregateProcessorProxy<>(wrapped, handlers.get(i));
        }
        return wrapped;
    }

    @Override
    public void aggregate(
        @NotNull List<GenericMergeableNode<I>> genericMergeableNodes) {getTarget().aggregate(genericMergeableNodes);}

    @Override
    public void aggregator(
        @NotNull GenericAggregator<I, O> aggregator) {getTarget().aggregator(aggregator);}

    @Override
    public GenericAggregator<I, O> getAggregator() {return getTarget().getAggregator();}

    @Override
    public List<GenericMergeableNode<I>> getMergeableNodes() {return getTarget().getMergeableNodes();}
}

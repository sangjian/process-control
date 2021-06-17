package cn.ideabuffer.process.core.processors.wrapper.proxy;

import cn.ideabuffer.process.core.aggregator.DistributeAggregator;
import cn.ideabuffer.process.core.nodes.DistributeMergeableNode;
import cn.ideabuffer.process.core.processors.DistributeAggregateProcessor;
import cn.ideabuffer.process.core.processors.DistributeProcessor;
import cn.ideabuffer.process.core.processors.wrapper.WrapperHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2021/06/17
 */
public class DistributeAggregateProcessorProxy<O> extends AbstractProcessorProxy<DistributeAggregateProcessor<O>, O>
    implements DistributeAggregateProcessor<O> {

    public DistributeAggregateProcessorProxy(
        @NotNull DistributeAggregateProcessor<O> target,
        @NotNull WrapperHandler<O> handler) {
        super(target, handler);
    }

    public static <O> DistributeAggregateProcessor<O> wrap(@NotNull DistributeAggregateProcessor<O> target, List<WrapperHandler<O>> handlers) {
        if (handlers == null || handlers.isEmpty()) {
            return target;
        }
        DistributeAggregateProcessor<O> wrapped = target;
        for (int i = handlers.size() - 1; i >= 0; i--) {
            wrapped = new DistributeAggregateProcessorProxy<>(wrapped, handlers.get(i));
        }
        return wrapped;
    }

    @Override
    public void aggregate(
        @NotNull List<DistributeMergeableNode<?, O>> distributeMergeableNodes) {
        getTarget().aggregate(distributeMergeableNodes);
    }

    @Override
    public void aggregator(
        @NotNull DistributeAggregator<O> aggregator) {getTarget().aggregator(aggregator);}

    @Override
    public DistributeAggregator<O> getAggregator() {return getTarget().getAggregator();}

    @Override
    public List<DistributeMergeableNode<?, O>> getMergeableNodes() {return getTarget().getMergeableNodes();}
}

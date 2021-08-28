package cn.ideabuffer.process.core.nodes.aggregate;

import cn.ideabuffer.process.core.nodes.DistributeMergeableNode;
import cn.ideabuffer.process.core.nodes.transmitter.AbstractTransmittableNode;
import cn.ideabuffer.process.core.processors.DistributeAggregateProcessor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/04/13
 */
public abstract class AbstractDistributeAggregatableNode<O>
    extends AbstractTransmittableNode<O, DistributeAggregateProcessor<O>> implements
    DistributeAggregatableNode<O> {

    private long timeout;

    protected AbstractDistributeAggregatableNode(@NotNull DistributeAggregateProcessor<O> processor) {
        this(0, processor);
    }

    protected AbstractDistributeAggregatableNode(long timeout, @NotNull DistributeAggregateProcessor<O> processor) {
        if (timeout < 0) {
            throw new IllegalArgumentException("timeout must > 0");
        }
        this.timeout = timeout;
        registerProcessor(processor);
    }

    @Override
    public void timeout(long timeout) {
        if (timeout < 0) {
            throw new IllegalArgumentException("timeout must > 0");
        }
        this.timeout = timeout;
    }

    @Override
    public long getTimeout() {
        return timeout;
    }

    @Override
    public List<DistributeMergeableNode<?, O>> getMergeableNodes() {
        return getProcessor().getMergeableNodes();
    }

    @Override
    public List<DistributeMergeableNode<?, O>> getNodes() {
        return getMergeableNodes();
    }
}

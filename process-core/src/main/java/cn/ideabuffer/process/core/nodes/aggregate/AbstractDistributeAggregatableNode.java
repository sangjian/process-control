package cn.ideabuffer.process.core.nodes.aggregate;

import cn.ideabuffer.process.core.nodes.transmitter.AbstractTransmittableNode;
import cn.ideabuffer.process.core.processors.DistributeAggregateProcessor;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/04/13
 */
public abstract class AbstractDistributeAggregatableNode<O>
    extends AbstractTransmittableNode<O, DistributeAggregateProcessor<O>> implements
    DistributeAggregatableNode<O> {

    private long timeout;

    public AbstractDistributeAggregatableNode(@NotNull DistributeAggregateProcessor<O> processor) {
        this(0, processor);
    }

    public AbstractDistributeAggregatableNode(long timeout, @NotNull DistributeAggregateProcessor<O> processor) {
        if (timeout < 0) {
            throw new IllegalArgumentException("timeout must > 0");
        }
        this.timeout = timeout;
        super.registerProcessor(processor);
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
}

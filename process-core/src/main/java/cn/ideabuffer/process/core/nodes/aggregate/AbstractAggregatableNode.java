package cn.ideabuffer.process.core.nodes.aggregate;

import cn.ideabuffer.process.core.nodes.GenericMergeableNode;
import cn.ideabuffer.process.core.nodes.transmitter.AbstractTransmittableNode;
import cn.ideabuffer.process.core.processors.AggregateProcessor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/04/13
 */
public abstract class AbstractAggregatableNode<I, O>
    extends AbstractTransmittableNode<O, AggregateProcessor<I, O>> implements
    AggregatableNode<I, O> {

    private long timeout;

    public AbstractAggregatableNode(@NotNull AggregateProcessor<I, O> processor) {
        this(0, processor);
    }

    public AbstractAggregatableNode(long timeout, @NotNull AggregateProcessor<I, O> processor) {
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

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    @Override
    public List<GenericMergeableNode<I>> getMergeableNodes() {
        return getProcessor().getMergeableNodes();
    }

    @Override
    public List<GenericMergeableNode<I>> getNodes() {
        return getMergeableNodes();
    }
}

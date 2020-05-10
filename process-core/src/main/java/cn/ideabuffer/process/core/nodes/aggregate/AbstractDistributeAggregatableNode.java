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

    public AbstractDistributeAggregatableNode(@NotNull DistributeAggregateProcessor<O> processor) {
        super.registerProcessor(processor);
    }

    @Override
    protected void onDestroy() {
        List<DistributeMergeableNode<?, O>> mergeableNodes = getProcessor().getMergeableNodes();
        if (mergeableNodes == null || mergeableNodes.isEmpty()) {
            return;
        }
        mergeableNodes.forEach((node) -> {
            try {
                node.destroy();
            } catch (Exception e) {
                logger.error("destroy encountered problem, node:{}", node, e);
            }
        });
    }
}

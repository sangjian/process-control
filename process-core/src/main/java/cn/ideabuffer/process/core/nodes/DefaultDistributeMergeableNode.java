package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.Lifecycle;
import cn.ideabuffer.process.core.LifecycleManager;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.context.KeyMapper;
import cn.ideabuffer.process.core.processors.DistributeProcessor;
import cn.ideabuffer.process.core.rules.Rule;

import java.util.Set;

/**
 * 分布式可合并节点
 *
 * @author sangjian.sj
 * @date 2020/05/09
 */
public class DefaultDistributeMergeableNode<T, R> extends AbstractMergeableNode
    implements DistributeMergeableNode<T, R> {

    private DistributeProcessor<T, R> processor;

    public DefaultDistributeMergeableNode() {
    }

    public DefaultDistributeMergeableNode(Rule rule, long timeout, DistributeProcessor<T, R> processor) {
        super(rule, timeout);
        this.processor = processor;
    }

    public DefaultDistributeMergeableNode(Rule rule, long timeout, DistributeProcessor<T, R> processor,
        Set<Key<?>> readableKeys,
        Set<Key<?>> writableKeys,
        KeyMapper keyMapper) {
        super(rule, timeout, readableKeys, writableKeys, keyMapper);
        this.processor = processor;
    }

    @Override
    public void registerProcessor(DistributeProcessor<T, R> processor) {
        this.processor = processor;
    }

    @Override
    public DistributeProcessor<T, R> getProcessor() {
        return processor;
    }

    @Override
    public void destroy() {
        super.destroy();
        if (processor instanceof Lifecycle) {
            LifecycleManager.destroy((Lifecycle)processor);
        }
    }

    @Override
    public void initialize() {
        if (processor == null) {
            throw new NullPointerException("processor cannot be null!");
        }
        super.initialize();
        if (processor instanceof Lifecycle) {
            LifecycleManager.initialize((Lifecycle)processor);
        }
    }

    @Override
    public String getName() {
        return name != null ? name : this.getClass().getSimpleName() + "-" + getProcessor().getClass().getSimpleName();
    }
}

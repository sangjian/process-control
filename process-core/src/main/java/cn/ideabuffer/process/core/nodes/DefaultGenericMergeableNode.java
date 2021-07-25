package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.Lifecycle;
import cn.ideabuffer.process.core.LifecycleManager;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.context.KeyMapper;
import cn.ideabuffer.process.core.rules.Rule;

import java.util.Set;

/**
 * @author sangjian.sj
 * @date 2020/05/09
 */
public class DefaultGenericMergeableNode<R> extends AbstractMergeableNode implements GenericMergeableNode<R> {

    private Processor<R> processor;

    public DefaultGenericMergeableNode() {
    }

    public DefaultGenericMergeableNode(Rule rule, long timeout, Processor<R> processor) {
        this(rule, timeout, processor, null, null, null);
    }

    public DefaultGenericMergeableNode(Rule rule, long timeout, Processor<R> processor, Set<Key<?>> readableKeys,
        Set<Key<?>> writableKeys, KeyMapper keyMapper) {
        super(rule, timeout, readableKeys, writableKeys, keyMapper);
        this.processor = processor;
    }

    @Override
    public void registerProcessor(Processor<R> processor) {
        this.processor = processor;
    }

    @Override
    public Processor<R> getProcessor() {
        return processor;
    }

    public void setProcessor(Processor<R> processor) {
        this.processor = processor;
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
}

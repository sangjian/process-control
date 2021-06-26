package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.Lifecycle;
import cn.ideabuffer.process.core.LifecycleManager;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.rules.Rule;

/**
 * @author sangjian.sj
 * @date 2020/05/09
 */
public class DefaultGenericMergeableNode<R> extends AbstractMergeableNode implements GenericMergeableNode<R> {

    private Processor<R> processor;

    public DefaultGenericMergeableNode() {
    }

    public DefaultGenericMergeableNode(Rule rule, long timeout, Processor<R> processor) {
        super(rule, timeout);
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

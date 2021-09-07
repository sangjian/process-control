package cn.ideabuffer.process.core.nodes.builder;

import cn.ideabuffer.process.core.ProcessListener;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.ReturnCondition;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.context.KeyMapper;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.nodes.branch.DefaultBranchNode;
import cn.ideabuffer.process.core.nodes.condition.WhileConditionNode;
import cn.ideabuffer.process.core.processors.WhileProcessor;
import cn.ideabuffer.process.core.processors.impl.WhileProcessorImpl;
import cn.ideabuffer.process.core.processors.wrapper.StatusWrapperHandler;
import cn.ideabuffer.process.core.processors.wrapper.proxy.WhileProcessorProxy;
import cn.ideabuffer.process.core.rules.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;

/**
 * @author sangjian.sj
 * @date 2021/07/03
 */
public class WhileNodeBuilder
    extends AbstractExecutableNodeBuilder<ProcessStatus, WhileProcessor, WhileConditionNode, StatusWrapperHandler> {

    private BranchNode branch;

    private WhileNodeBuilder() {
        super(new WhileConditionNode());
    }

    public static WhileNodeBuilder newBuilder() {
        return new WhileNodeBuilder();
    }

    @Override
    public WhileNodeBuilder parallel() {
        super.parallel();
        return this;
    }

    @Override
    public WhileNodeBuilder parallel(Executor executor) {
        super.parallel(executor);
        return this;
    }

    @Override
    public WhileNodeBuilder processOn(Rule rule) {
        super.processOn(rule);
        return this;
    }

    @Override
    public WhileNodeBuilder addListeners(ProcessListener<ProcessStatus>... listeners) {
        super.addListeners(listeners);
        return this;
    }

    @Override
    public WhileNodeBuilder by(WhileProcessor processor) {
        super.by(processor);
        return this;
    }

    @Override
    public WhileNodeBuilder resultKey(Key<ProcessStatus> resultKey) {
        super.resultKey(resultKey);
        return this;
    }

    @Override
    public WhileNodeBuilder returnOn(ReturnCondition<ProcessStatus> condition) {
        super.returnOn(condition);
        return this;
    }

    @Override
    public WhileNodeBuilder keyMapper(KeyMapper keyMapper) {
        super.keyMapper(keyMapper);
        return this;
    }

    @Override
    public WhileNodeBuilder readableKeys(@NotNull Key<?>... keys) {
        super.readableKeys(keys);
        return this;
    }

    @Override
    public WhileNodeBuilder readableKeys(@NotNull Set<Key<?>> keys) {
        super.readableKeys(keys);
        return this;
    }

    @Override
    public WhileNodeBuilder enabled(BooleanSupplier supplier) {
        super.enabled(supplier);
        return this;
    }

    @Override
    public WhileNodeBuilder wrap(@NotNull StatusWrapperHandler handler) {
        super.wrap(handler);
        return this;
    }

    @Override
    public WhileNodeBuilder wrap(@NotNull List<StatusWrapperHandler> handlers) {
        super.wrap(handlers);
        return this;
    }

    public WhileNodeBuilder then(BranchNode branch) {
        this.branch = branch;
        return this;
    }

    public WhileNodeBuilder then(ExecutableNode<?, ?>... nodes) {
        return then(new DefaultBranchNode(nodes));
    }

    public WhileNodeBuilder then(List<ExecutableNode<?, ?>> nodes) {
        return then(new DefaultBranchNode(null, nodes));
    }

    @Override
    public WhileNodeBuilder name(String name) {
        super.name(name);
        return this;
    }

    @Override
    public WhileNodeBuilder description(String description) {
        super.description(description);
        return this;
    }

    @Override
    public WhileNodeBuilder strongDependency() {
        super.strongDependency();
        return this;
    }

    @Override
    public WhileNodeBuilder weakDependency() {
        super.weakDependency();
        return this;
    }

    @Override
    public WhileNodeBuilder weakDependency(@NotNull BooleanSupplier supplier) {
        super.weakDependency(supplier);
        return this;
    }

    @Override
    public WhileNodeBuilder fallbackBy(Processor<ProcessStatus> fallbackProcessor) {
        super.fallbackBy(fallbackProcessor);
        return this;
    }

    @Override
    public WhileConditionNode build() {
        if (rule == null) {
            throw new NullPointerException("rule cannot be null!");
        }
        if (processor == null) {
            processor = new WhileProcessorImpl(rule, branch, null);
        } else {
            processor.setRule(rule);
            processor.setBranch(branch);
        }
        processor = WhileProcessorProxy.wrap(processor, handlers);
        WhileConditionNode node = super.build();
        processor.setKeyManager(node);
        return node;
    }
}

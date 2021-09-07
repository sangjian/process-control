package cn.ideabuffer.process.core.nodes.builder;

import cn.ideabuffer.process.core.ProcessListener;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.context.KeyMapper;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.nodes.Nodes;
import cn.ideabuffer.process.core.nodes.ParallelBranchNode;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.processors.ParallelBranchProcessor;
import cn.ideabuffer.process.core.processors.impl.ParallelBranchProcessorImpl;
import cn.ideabuffer.process.core.processors.wrapper.StatusWrapperHandler;
import cn.ideabuffer.process.core.processors.wrapper.proxy.ParallelBranchProcessorProxy;
import cn.ideabuffer.process.core.rules.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;
import cn.ideabuffer.process.core.strategies.ProceedStrategy;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;

/**
 * @author sangjian.sj
 * @date 2020/04/24
 */
public class ParallelBranchNodeBuilder
    extends
    AbstractExecutableNodeBuilder<ProcessStatus, ParallelBranchProcessor, ParallelBranchNode, StatusWrapperHandler> {

    private List<BranchNode> branches;

    private ProceedStrategy strategy;

    private ParallelBranchNodeBuilder() {
        super(Nodes.newParallelBranchNode());
        this.branches = new ArrayList<>();
    }

    public static ParallelBranchNodeBuilder newBuilder() {
        return new ParallelBranchNodeBuilder();
    }

    @Override
    public ParallelBranchNodeBuilder parallel(Executor executor) {
        super.parallel(executor);
        return this;
    }

    @Override
    public ParallelBranchNodeBuilder processOn(Rule rule) {
        super.processOn(rule);
        return this;
    }

    @Override
    public ParallelBranchNodeBuilder addListeners(ProcessListener<ProcessStatus>... listeners) {
        super.addListeners(listeners);
        return this;
    }

    @Override
    public ParallelBranchNodeBuilder by(ParallelBranchProcessor processor) {
        super.by(processor);
        return this;
    }

    public ParallelBranchNodeBuilder addBranch(@NotNull ExecutableNode<?, ?>... nodes) {
        this.branches.add(Nodes.newBranch(nodes));
        return this;
    }

    public ParallelBranchNodeBuilder addBranch(@NotNull BranchNode branch) {
        this.branches.add(branch);
        return this;
    }

    public ParallelBranchNodeBuilder proceedWhen(@NotNull ProceedStrategy strategy) {
        this.strategy = strategy;
        return this;
    }

    @Override
    public ParallelBranchNodeBuilder enabled(BooleanSupplier supplier) {
        super.enabled(supplier);
        return this;
    }

    @Override
    public ParallelBranchNodeBuilder wrap(@NotNull StatusWrapperHandler handler) {
        super.wrap(handler);
        return this;
    }

    @Override
    public ParallelBranchNodeBuilder wrap(@NotNull List<StatusWrapperHandler> wrapperHandlers) {
        super.wrap(wrapperHandlers);
        return this;
    }

    @Override
    public ParallelBranchNodeBuilder name(String name) {
        super.name(name);
        return this;
    }

    @Override
    public ParallelBranchNodeBuilder description(String description) {
        super.description(description);
        return this;
    }

    @Override
    protected ParallelBranchNodeBuilder keyMapper(KeyMapper keyMapper) {
        super.keyMapper(keyMapper);
        return this;
    }

    @Override
    protected ParallelBranchNodeBuilder readableKeys(@NotNull Key<?>... keys) {
        super.readableKeys(keys);
        return this;
    }

    @Override
    protected ParallelBranchNodeBuilder readableKeys(@NotNull Set<Key<?>> keys) {
        super.readableKeys(keys);
        return this;
    }

    @Override
    public ParallelBranchNodeBuilder strongDependency() {
        super.strongDependency();
        return this;
    }

    @Override
    public ParallelBranchNodeBuilder weakDependency() {
        super.weakDependency();
        return this;
    }

    @Override
    public ParallelBranchNodeBuilder weakDependency(@NotNull BooleanSupplier supplier) {
        super.weakDependency(supplier);
        return this;
    }

    @Override
    public ParallelBranchNodeBuilder fallbackBy(Processor<ProcessStatus> fallbackProcessor) {
        super.fallbackBy(fallbackProcessor);
        return this;
    }

    @Override
    public ParallelBranchNode build() {
        if (processor == null) {
            processor = new ParallelBranchProcessorImpl();
        }
        processor = ParallelBranchProcessorProxy.wrap(processor, handlers);
        ParallelBranchNode node = super.build();
        this.branches.forEach(processor::addBranch);
        if (strategy != null) {
            processor.proceedWhen(strategy);
        }
        processor.parallelBy(executor);
        return node;
    }
}

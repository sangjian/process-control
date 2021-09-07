package cn.ideabuffer.process.core.nodes.builder;

import cn.ideabuffer.process.core.ProcessListener;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.context.KeyMapper;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.nodes.Nodes;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.processors.BranchProcessor;
import cn.ideabuffer.process.core.processors.impl.BranchProcessorImpl;
import cn.ideabuffer.process.core.processors.wrapper.StatusWrapperHandler;
import cn.ideabuffer.process.core.processors.wrapper.proxy.BranchProcessorProxy;
import cn.ideabuffer.process.core.rules.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;

/**
 * @author sangjian.sj
 * @date 2020/04/24
 */
public class BranchNodeBuilder
    extends AbstractExecutableNodeBuilder<ProcessStatus, BranchProcessor, BranchNode, StatusWrapperHandler> {

    private List<ExecutableNode<?, ?>> nodes = new LinkedList<>();

    private BranchNodeBuilder() {
        super(Nodes.newBranch());
    }

    public static BranchNodeBuilder newBuilder() {
        return new BranchNodeBuilder();
    }

    @Override
    public BranchNodeBuilder parallel() {
        super.parallel();
        return this;
    }

    @Override
    public BranchNodeBuilder parallel(Executor executor) {
        super.parallel(executor);
        return this;
    }

    @Override
    public BranchNodeBuilder processOn(Rule rule) {
        super.processOn(rule);
        return this;
    }

    @Override
    public BranchNodeBuilder addListeners(ProcessListener<ProcessStatus>... listeners) {
        super.addListeners(listeners);
        return this;
    }

    public BranchNodeBuilder addNodes(@NotNull ExecutableNode<?, ?>... nodes) {
        this.nodes.addAll(Arrays.stream(nodes).collect(Collectors.toList()));
        return this;
    }

    @Override
    public BranchNodeBuilder by(BranchProcessor processor) {
        super.by(processor);
        return this;
    }

    @Override
    public BranchNodeBuilder enabled(BooleanSupplier supplier) {
        super.enabled(supplier);
        return this;
    }

    @Override
    public BranchNodeBuilder wrap(@NotNull StatusWrapperHandler handler) {
        super.wrap(handler);
        return this;
    }

    @Override
    public BranchNodeBuilder wrap(@NotNull List<StatusWrapperHandler> wrapperHandlers) {
        super.wrap(wrapperHandlers);
        return this;
    }

    @Override
    public BranchNodeBuilder name(String name) {
        super.name(name);
        return this;
    }

    @Override
    protected BranchNodeBuilder keyMapper(KeyMapper keyMapper) {
        super.keyMapper(keyMapper);
        return this;
    }

    @Override
    protected BranchNodeBuilder readableKeys(@NotNull Key<?>... keys) {
        super.readableKeys(keys);
        return this;
    }

    @Override
    protected BranchNodeBuilder readableKeys(@NotNull Set<Key<?>> keys) {
        super.readableKeys(keys);
        return this;
    }

    @Override
    public BranchNodeBuilder description(String description) {
        super.description(description);
        return this;
    }

    @Override
    public BranchNodeBuilder strongDependency() {
        super.strongDependency();
        return this;
    }

    @Override
    public BranchNodeBuilder weakDependency() {
        super.weakDependency();
        return this;
    }

    @Override
    public BranchNodeBuilder weakDependency(@NotNull BooleanSupplier supplier) {
        super.weakDependency(supplier);
        return this;
    }

    @Override
    public BranchNodeBuilder fallbackBy(Processor<ProcessStatus> fallbackProcessor) {
        super.fallbackBy(fallbackProcessor);
        return this;
    }

    @Override
    public BranchNode build() {
        if (processor == null) {
            processor = new BranchProcessorImpl();
        }
        processor = BranchProcessorProxy.wrap(processor, handlers);
        BranchNode node = super.build();
        processor.addNodes(nodes.toArray(new ExecutableNode[0]));
        return node;
    }
}

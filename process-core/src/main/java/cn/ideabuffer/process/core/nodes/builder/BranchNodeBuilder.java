package cn.ideabuffer.process.core.nodes.builder;

import cn.ideabuffer.process.core.ProcessListener;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.nodes.Nodes;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.processors.BranchProcessor;
import cn.ideabuffer.process.core.processors.impl.BranchProcessorImpl;
import cn.ideabuffer.process.core.processors.wrapper.WrapperHandler;
import cn.ideabuffer.process.core.processors.wrapper.proxy.BranchProcessorProxy;
import cn.ideabuffer.process.core.rule.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;

/**
 * @author sangjian.sj
 * @date 2020/04/24
 */
public class BranchNodeBuilder extends AbstractExecutableNodeBuilder<ProcessStatus, BranchProcessor, BranchNode> {

    private ExecutableNode[] nodes;

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
        this.nodes = nodes;
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
    public BranchNodeBuilder wrap(@NotNull WrapperHandler<ProcessStatus> handler) {
        super.wrap(handler);
        return this;
    }

    @Override
    public BranchNodeBuilder wrap(@NotNull List<WrapperHandler<ProcessStatus>> wrapperHandlers) {
        super.wrap(wrapperHandlers);
        return this;
    }

    @Override
    public BranchNode build() {
        if (processor == null) {
            processor = new BranchProcessorImpl();
        }
        processor = BranchProcessorProxy.wrap(processor, handlers);
        BranchNode node = super.build();
        processor.addNodes(nodes);
        return node;
    }
}

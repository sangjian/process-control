package cn.ideabuffer.process.core.nodes.builder;

import cn.ideabuffer.process.core.ProcessListener;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.context.KeyMapper;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.nodes.Nodes;
import cn.ideabuffer.process.core.nodes.TryCatchFinallyNode;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.nodes.branch.DefaultBranchNode;
import cn.ideabuffer.process.core.processors.TryCatchFinallyProcessor;
import cn.ideabuffer.process.core.processors.impl.TryCatchFinallyProcessorImpl;
import cn.ideabuffer.process.core.processors.wrapper.StatusWrapperHandler;
import cn.ideabuffer.process.core.processors.wrapper.proxy.TryCatchFinallyProcessorProxy;
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
 * @date 2021/07/03
 */
public class TryCatchFinallyNodeBuilder extends
    AbstractExecutableNodeBuilder<ProcessStatus, TryCatchFinallyProcessor, TryCatchFinallyNode, StatusWrapperHandler> {

    private BranchNode tryBranch;
    private List<TryCatchFinallyNode.CatchMapper> catchMapperList;
    private BranchNode finallyBranch;

    private TryCatchFinallyNodeBuilder() {
        super(new TryCatchFinallyNode());
        this.catchMapperList = new LinkedList<>();
    }

    public static TryCatchFinallyNodeBuilder newBuilder() {
        return new TryCatchFinallyNodeBuilder();
    }

    @Override
    public TryCatchFinallyNodeBuilder parallel() {
        super.parallel();
        return this;
    }

    @Override
    public TryCatchFinallyNodeBuilder parallel(Executor executor) {
        super.parallel(executor);
        return this;
    }

    @Override
    public TryCatchFinallyNodeBuilder processOn(Rule rule) {
        super.processOn(rule);
        return this;
    }

    @Override
    public TryCatchFinallyNodeBuilder addListeners(ProcessListener<ProcessStatus>... listeners) {
        super.addListeners(listeners);
        return this;
    }

    @Override
    public TryCatchFinallyNodeBuilder by(TryCatchFinallyProcessor processor) {
        super.by(processor);
        return this;
    }

    @Override
    public TryCatchFinallyNodeBuilder enabled(BooleanSupplier supplier) {
        super.enabled(supplier);
        return this;
    }

    @Override
    public TryCatchFinallyNodeBuilder wrap(@NotNull StatusWrapperHandler handler) {
        super.wrap(handler);
        return this;
    }

    @Override
    public TryCatchFinallyNodeBuilder wrap(@NotNull List<StatusWrapperHandler> handlers) {
        super.wrap(handlers);
        return this;
    }

    public TryCatchFinally tryOn(BranchNode branch) {
        this.tryBranch = branch;
        return new TryCatchFinally(this);
    }

    public TryCatchFinally tryOn(ExecutableNode<?, ?>... nodes) {
        return tryOn(new DefaultBranchNode(nodes));
    }

    public TryCatchFinally tryOn(List<ExecutableNode<?, ?>> nodes) {
        return tryOn(new DefaultBranchNode(nodes));
    }

    @Override
    public TryCatchFinallyNodeBuilder name(String name) {
        super.name(name);
        return this;
    }

    @Override
    public TryCatchFinallyNodeBuilder description(String description) {
        super.description(description);
        return this;
    }

    @Override
    protected TryCatchFinallyNodeBuilder keyMapper(KeyMapper keyMapper) {
        super.keyMapper(keyMapper);
        return this;
    }

    @Override
    protected TryCatchFinallyNodeBuilder readableKeys(@NotNull Key<?>... keys) {
        super.readableKeys(keys);
        return this;
    }

    @Override
    protected TryCatchFinallyNodeBuilder readableKeys(@NotNull Set<Key<?>> keys) {
        super.readableKeys(keys);
        return this;
    }

    @Override
    public TryCatchFinallyNodeBuilder strongDependency() {
        super.strongDependency();
        return this;
    }

    @Override
    public TryCatchFinallyNodeBuilder weakDependency() {
        super.weakDependency();
        return this;
    }

    @Override
    public TryCatchFinallyNodeBuilder weakDependency(@NotNull BooleanSupplier supplier) {
        super.weakDependency(supplier);
        return this;
    }

    @Override
    public TryCatchFinallyNodeBuilder fallbackBy(Processor<ProcessStatus> fallbackProcessor) {
        super.fallbackBy(fallbackProcessor);
        return this;
    }

    @Override
    public TryCatchFinallyNode build() {
        if (processor == null) {
            processor = new TryCatchFinallyProcessorImpl(tryBranch, catchMapperList, finallyBranch);
        } else {
            processor.setTryBranch(tryBranch);
            processor.setCatchMapperList(catchMapperList);
            processor.setFinallyBranch(finallyBranch);
        }
        processor = TryCatchFinallyProcessorProxy.wrap(processor, handlers);
        return super.build();
    }

    public static class TryCatchFinally {
        private TryCatchFinallyNodeBuilder builder;

        public TryCatchFinally(TryCatchFinallyNodeBuilder builder) {
            this.builder = builder;
        }

        public TryCatchFinally catchOn(Class<? extends Throwable> expClass, BranchNode branch) {
            if (expClass == null) {
                throw new NullPointerException();
            }
            builder.catchMapperList.add(new TryCatchFinallyNode.CatchMapper(expClass, branch));
            return this;
        }

        public TryCatchFinally catchOn(@NotNull Class<? extends Throwable> expClass, ExecutableNode<?, ?>... nodes) {
            return catchOn(expClass, new DefaultBranchNode(nodes));
        }

        public TryCatchFinally catchOn(@NotNull Class<? extends Throwable> expClass, List<ExecutableNode<?, ?>> nodes) {
            return catchOn(expClass, new DefaultBranchNode(nodes));
        }

        public TryCatchFinally catchOn(@NotNull Class<? extends Throwable> expClass, Processor<?>... processors) {
            if (processors == null || processors.length == 0) {
                return catchOn(expClass, new DefaultBranchNode());
            }
            List<ExecutableNode<?, ?>> nodes = Arrays.stream(processors).map(Nodes::newProcessNode).collect(
                Collectors.toList());
            return catchOn(expClass, new DefaultBranchNode(nodes));
        }

        public TryCatchFinally doFinally(BranchNode finallyBranch) {
            builder.finallyBranch = finallyBranch;
            return this;
        }

        public TryCatchFinally doFinally(ExecutableNode<?, ?>... nodes) {
            return doFinally(new DefaultBranchNode(nodes));
        }

        public TryCatchFinally doFinally(Processor<?>... processors) {
            BranchNode branch = null;
            if (processors == null || processors.length == 0) {
                return doFinally(branch);
            }
            branch = new DefaultBranchNode(processors);
            return doFinally(branch);
        }

        public TryCatchFinally doFinally(List<ExecutableNode<?, ?>> nodes) {
            return doFinally(new DefaultBranchNode(nodes));
        }

        public TryCatchFinallyNode build() {
            return builder.build();
        }
    }
}

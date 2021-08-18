package cn.ideabuffer.process.core.nodes.builder;

import cn.ideabuffer.process.core.ProcessListener;
import cn.ideabuffer.process.core.ReturnCondition;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.context.KeyMapper;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.nodes.branch.DefaultBranchNode;
import cn.ideabuffer.process.core.nodes.condition.IfConditionNode;
import cn.ideabuffer.process.core.processors.IfProcessor;
import cn.ideabuffer.process.core.processors.impl.IfProcessorImpl;
import cn.ideabuffer.process.core.processors.wrapper.StatusWrapperHandler;
import cn.ideabuffer.process.core.processors.wrapper.proxy.IfProcessorProxy;
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
public class IfNodeBuilder extends AbstractExecutableNodeBuilder<ProcessStatus, IfProcessor, IfConditionNode, StatusWrapperHandler> {

    private BranchNode trueBranch;
    private BranchNode falseBranch;

    private IfNodeBuilder() {
        super(new IfConditionNode());
    }

    public static IfNodeBuilder newBuilder() {
        return new IfNodeBuilder();
    }

    @Override
    public IfNodeBuilder parallel() {
        super.parallel();
        return this;
    }

    @Override
    public IfNodeBuilder parallel(Executor executor) {
        super.parallel(executor);
        return this;
    }

    @Override
    public IfNodeBuilder processOn(Rule rule) {
        super.processOn(rule);
        return this;
    }

    @Override
    public IfNodeBuilder addListeners(ProcessListener<ProcessStatus>... listeners) {
        super.addListeners(listeners);
        return this;
    }

    @Override
    public IfNodeBuilder by(IfProcessor processor) {
        super.by(processor);
        return this;
    }

    @Override
    public IfNodeBuilder resultKey(Key<ProcessStatus> resultKey) {
        super.resultKey(resultKey);
        return this;
    }

    @Override
    public IfNodeBuilder returnOn(ReturnCondition<ProcessStatus> condition) {
        super.returnOn(condition);
        return this;
    }

    @Override
    public IfNodeBuilder keyMapper(KeyMapper keyMapper) {
        super.keyMapper(keyMapper);
        return this;
    }

    @Override
    public IfNodeBuilder readableKeys(@NotNull Key<?>... keys) {
        super.readableKeys(keys);
        return this;
    }

    @Override
    public IfNodeBuilder readableKeys(@NotNull Set<Key<?>> keys) {
        super.readableKeys(keys);
        return this;
    }

    @Override
    public IfNodeBuilder writableKeys(@NotNull Key<?>... keys) {
        super.writableKeys(keys);
        return this;
    }

    @Override
    public IfNodeBuilder writableKeys(@NotNull Set<Key<?>> keys) {
        super.writableKeys(keys);
        return this;
    }

    @Override
    public IfNodeBuilder enabled(BooleanSupplier supplier) {
        super.enabled(supplier);
        return this;
    }

    @Override
    public IfNodeBuilder wrap(@NotNull StatusWrapperHandler handler) {
        super.wrap(handler);
        return this;
    }

    @Override
    public IfNodeBuilder wrap(@NotNull List<StatusWrapperHandler> handlers) {
        super.wrap(handlers);
        return this;
    }

    public IfWhenBuilder then(@NotNull BranchNode branch) {
        this.trueBranch = branch;
        return new IfWhenBuilder(this);
    }

    public IfWhenBuilder then(@NotNull ExecutableNode<?, ?>... nodes) {
        return then(new DefaultBranchNode(nodes));
    }

    public IfWhenBuilder then(@NotNull List<ExecutableNode<?, ?>> nodes) {
        return then(new DefaultBranchNode(nodes));
    }

    @Override
    public IfNodeBuilder name(String name) {
        super.name(name);
        return this;
    }

    @Override
    public IfNodeBuilder description(String description) {
        super.description(description);
        return this;
    }

    @Override
    public IfConditionNode build() {
        if (processor == null) {
            processor = new IfProcessorImpl(rule, trueBranch, falseBranch);
        } else {
            processor.setRule(rule);
            processor.setTrueBranch(trueBranch);
            processor.setFalseBranch(falseBranch);
        }

        processor = IfProcessorProxy.wrap(processor, handlers);

        IfConditionNode node = super.build();
        processor.setKeyManager(node);
        return node;
    }

    public static class IfWhenBuilder {

        private IfNodeBuilder builder;

        IfWhenBuilder(IfNodeBuilder builder) {
            this.builder = builder;
        }

        public IfWhenBuilder otherwise(BranchNode falseBranch) {
            builder.falseBranch = falseBranch;
            return this;
        }

        public IfWhenBuilder otherwise(ExecutableNode<?, ?>... nodes) {
            return otherwise(new DefaultBranchNode(nodes));
        }

        public IfConditionNode build() {
            return builder.build();
        }

    }
}

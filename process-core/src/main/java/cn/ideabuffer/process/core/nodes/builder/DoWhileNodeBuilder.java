package cn.ideabuffer.process.core.nodes.builder;

import cn.ideabuffer.process.core.ProcessListener;
import cn.ideabuffer.process.core.ReturnCondition;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.context.KeyMapper;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.nodes.branch.DefaultBranchNode;
import cn.ideabuffer.process.core.nodes.condition.DoWhileConditionNode;
import cn.ideabuffer.process.core.processors.DoWhileProcessor;
import cn.ideabuffer.process.core.processors.impl.DoWhileProcessorImpl;
import cn.ideabuffer.process.core.processors.wrapper.StatusWrapperHandler;
import cn.ideabuffer.process.core.processors.wrapper.proxy.DoWhileProcessorProxy;
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
public class DoWhileNodeBuilder
    extends AbstractExecutableNodeBuilder<ProcessStatus, DoWhileProcessor, DoWhileConditionNode, StatusWrapperHandler> {

    private BranchNode branch;

    private DoWhileNodeBuilder() {
        super(new DoWhileConditionNode());
    }

    public static DoWhileNodeBuilder newBuilder() {
        return new DoWhileNodeBuilder();
    }

    @Override
    public DoWhileNodeBuilder parallel() {
        super.parallel();
        return this;
    }

    @Override
    public DoWhileNodeBuilder parallel(Executor executor) {
        super.parallel(executor);
        return this;
    }

    @Override
    public DoWhileNodeBuilder processOn(Rule rule) {
        super.processOn(rule);
        return this;
    }

    @Override
    public DoWhileNodeBuilder addListeners(ProcessListener<ProcessStatus>... listeners) {
        super.addListeners(listeners);
        return this;
    }

    @Override
    public DoWhileNodeBuilder by(DoWhileProcessor processor) {
        super.by(processor);
        return this;
    }

    @Override
    public DoWhileNodeBuilder resultKey(Key<ProcessStatus> resultKey) {
        super.resultKey(resultKey);
        return this;
    }

    @Override
    public DoWhileNodeBuilder returnOn(ReturnCondition<ProcessStatus> condition) {
        super.returnOn(condition);
        return this;
    }

    @Override
    public DoWhileNodeBuilder keyMapper(KeyMapper keyMapper) {
        super.keyMapper(keyMapper);
        return this;
    }

    @Override
    public DoWhileNodeBuilder readableKeys(@NotNull Key<?>... keys) {
        super.readableKeys(keys);
        return this;
    }

    @Override
    public DoWhileNodeBuilder readableKeys(@NotNull Set<Key<?>> keys) {
        super.readableKeys(keys);
        return this;
    }

    @Override
    public DoWhileNodeBuilder writableKeys(@NotNull Key<?>... keys) {
        super.writableKeys(keys);
        return this;
    }

    @Override
    public DoWhileNodeBuilder writableKeys(@NotNull Set<Key<?>> keys) {
        super.writableKeys(keys);
        return this;
    }

    @Override
    public DoWhileNodeBuilder enabled(BooleanSupplier supplier) {
        super.enabled(supplier);
        return this;
    }

    @Override
    public DoWhileNodeBuilder wrap(@NotNull StatusWrapperHandler handler) {
        super.wrap(handler);
        return this;
    }

    @Override
    public DoWhileNodeBuilder wrap(@NotNull List<StatusWrapperHandler> handlers) {
        super.wrap(handlers);
        return this;
    }

    public DoWhileNodeBuilder then(BranchNode branch) {
        this.branch = branch;
        return this;
    }

    public DoWhileNodeBuilder then(ExecutableNode<?, ?>... nodes) {
        return then(new DefaultBranchNode(nodes));
    }

    public DoWhileNodeBuilder then(List<ExecutableNode<?, ?>> nodes) {
        return then(new DefaultBranchNode(null, nodes));
    }

    @Override
    public DoWhileConditionNode build() {
        if (rule == null) {
            throw new NullPointerException("rule cannot be null!");
        }
        if (processor == null) {
            processor = new DoWhileProcessorImpl(rule, branch, keyMapper, readableKeys, writableKeys);
        } else {
            processor.setRule(rule);
            processor.setBranch(branch);
            processor.setKeyMapper(keyMapper);
            processor.setReadableKeys(readableKeys);
            processor.setWritableKeys(writableKeys);
        }
        processor = DoWhileProcessorProxy.wrap(processor, handlers);
        return super.build();
    }
}

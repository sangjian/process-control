package cn.ideabuffer.process.core.nodes.condition;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.context.KeyMapper;
import cn.ideabuffer.process.core.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.processors.WhileProcessor;
import cn.ideabuffer.process.core.processors.impl.WhileProcessorImpl;
import cn.ideabuffer.process.core.rules.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public class WhileConditionNode extends AbstractExecutableNode<ProcessStatus, WhileProcessor> {

    public WhileConditionNode(@NotNull Rule rule, @NotNull BranchNode branch) {
        this(rule, branch, null, null, null);
    }

    public WhileConditionNode(@NotNull Rule rule, @NotNull BranchNode branch, KeyMapper keyMapper,
        Set<Key<?>> readableKeys, Set<Key<?>> writableKeys) {
        this(new WhileProcessorImpl(rule, branch, keyMapper, readableKeys, writableKeys));
    }

    public WhileConditionNode(@NotNull WhileProcessor processor) {
        super.registerProcessor(processor);
    }

    @Override
    public KeyMapper getKeyMapper() {
        return getProcessor().getKeyMapper();
    }

    @Override
    public void setKeyMapper(KeyMapper mapper) {
        getProcessor().setKeyMapper(mapper);
    }

    @Override
    public Set<Key<?>> getReadableKeys() {
        return getProcessor().getReadableKeys();
    }

    @Override
    public void setReadableKeys(Set<Key<?>> keys) {
        getProcessor().setReadableKeys(keys);
    }

    @Override
    public Set<Key<?>> getWritableKeys() {
        return getProcessor().getWritableKeys();
    }

    @Override
    public void setWritableKeys(Set<Key<?>> keys) {
        getProcessor().setWritableKeys(keys);
    }

    @Override
    protected boolean ruleCheck(@NotNull Context context) {
        return true;
    }
}

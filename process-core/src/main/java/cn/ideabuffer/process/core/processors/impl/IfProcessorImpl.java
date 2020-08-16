package cn.ideabuffer.process.core.processors.impl;

import cn.ideabuffer.process.core.block.InnerBlock;
import cn.ideabuffer.process.core.context.*;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.processors.IfProcessor;
import cn.ideabuffer.process.core.rule.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * @author sangjian.sj
 * @date 2020/05/02
 */
public class IfProcessorImpl implements IfProcessor {

    private Rule rule;
    private BranchNode trueBranch;
    private BranchNode falseBranch;
    private KeyMapper keyMapper;
    private Set<Key<?>> readableKeys;
    private Set<Key<?>> writableKeys;

    public IfProcessorImpl(@NotNull Rule rule, @NotNull BranchNode trueBranch) {
        this(rule, trueBranch, null);
    }

    public IfProcessorImpl(@NotNull Rule rule, @NotNull BranchNode trueBranch, BranchNode falseBranch) {
        this(rule, trueBranch, falseBranch, null, null, null);
    }

    public IfProcessorImpl(@NotNull Rule rule, @NotNull BranchNode trueBranch, BranchNode falseBranch, KeyMapper keyMapper, Set<Key<?>> readableKeys, Set<Key<?>> writableKeys) {
        this.rule = rule;
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
        this.keyMapper = keyMapper;
        this.readableKeys = readableKeys;
        this.writableKeys = writableKeys;
    }

    @Override
    public Rule getRule() {
        return this.rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    @Override
    public BranchNode getTrueBranch() {
        return this.trueBranch;
    }

    public void setTrueBranch(BranchNode trueBranch) {
        this.trueBranch = trueBranch;
    }

    @Override
    public BranchNode getFalseBranch() {
        return this.falseBranch;
    }

    public void setFalseBranch(BranchNode falseBranch) {
        this.falseBranch = falseBranch;
    }

    @Override
    public KeyMapper getKeyMapper() {
        return keyMapper;
    }

    @Override
    public void setKeyMapper(KeyMapper keyMapper) {
        this.keyMapper = keyMapper;
    }

    @Override
    public Set<Key<?>> getReadableKeys() {
        return readableKeys;
    }

    @Override
    public void setReadableKeys(Set<Key<?>> readableKeys) {
        this.readableKeys = readableKeys;
    }

    @Override
    public Set<Key<?>> getWritableKeys() {
        return writableKeys;
    }

    @Override
    public void setWritableKeys(Set<Key<?>> writableKeys) {
        this.writableKeys = writableKeys;
    }

    @NotNull
    @Override
    public ProcessStatus process(@NotNull Context context) throws Exception {
        if (rule == null) {
            throw new NullPointerException("rule can't be null");
        }
        InnerBlock ifBlock = new InnerBlock(context.getBlock());
        ContextWrapper contextWrapper = Contexts.wrap(context, ifBlock, keyMapper, readableKeys, writableKeys);
        BranchNode branch = rule.match(contextWrapper) ? trueBranch : falseBranch;
        if (branch == null) {
            return ProcessStatus.proceed();
        }
        return branch.execute(contextWrapper);
    }
}

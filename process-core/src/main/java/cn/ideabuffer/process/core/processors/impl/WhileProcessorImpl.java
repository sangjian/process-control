package cn.ideabuffer.process.core.processors.impl;

import cn.ideabuffer.process.core.block.InnerBlock;
import cn.ideabuffer.process.core.context.*;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.processors.WhileProcessor;
import cn.ideabuffer.process.core.rule.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * @author sangjian.sj
 * @date 2020/05/02
 */
public class WhileProcessorImpl implements WhileProcessor {

    private Rule rule;
    private BranchNode branch;
    private KeyMapper keyMapper;
    private Set<Key<?>> requiredKeys;

    public WhileProcessorImpl(Rule rule, BranchNode branch, KeyMapper keyMapper, Set<Key<?>> requiredKeys) {
        this.rule = rule;
        this.branch = branch;
        this.keyMapper = keyMapper;
        this.requiredKeys = requiredKeys;
    }

    @Override
    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    @Override
    public BranchNode getBranch() {
        return branch;
    }

    public void setBranch(BranchNode branch) {
        this.branch = branch;
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
    public Set<Key<?>> getRequiredKeys() {
        return requiredKeys;
    }

    @Override
    public void setRequiredKeys(Set<Key<?>> requiredKeys) {
        this.requiredKeys = requiredKeys;
    }

    @NotNull
    @Override
    public ProcessStatus process(@NotNull Context context) throws Exception {
        if (getRule() == null) {
            throw new NullPointerException("rule can't be null");
        }
        if (branch == null) {
            return ProcessStatus.proceed();
        }

        InnerBlock whileBlock = new InnerBlock(true, true, context.getBlock());
        ContextWrapper whileContext = Contexts.wrap(context, whileBlock, keyMapper, requiredKeys);

        while (getRule().match(whileContext)) {
            ProcessStatus status = branch.execute(whileContext);
            if (ProcessStatus.isComplete(status)) {
                return status;
            }
            if (whileBlock.hasBroken()) {
                break;
            }

            whileBlock.resetBreak();
            whileBlock.resetContinue();
        }

        return ProcessStatus.proceed();
    }
}

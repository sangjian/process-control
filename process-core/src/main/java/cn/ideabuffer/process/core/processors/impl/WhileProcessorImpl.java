package cn.ideabuffer.process.core.processors.impl;

import cn.ideabuffer.process.core.block.Block;
import cn.ideabuffer.process.core.block.BlockWrapper;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.ContextWrapper;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.processors.WhileProcessor;
import cn.ideabuffer.process.core.rule.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/05/02
 */
public class WhileProcessorImpl implements WhileProcessor {

    private Rule rule;
    private BranchNode branch;

    public WhileProcessorImpl(Rule rule, BranchNode branch) {
        this.rule = rule;
        this.branch = branch;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public void setBranch(BranchNode branch) {
        this.branch = branch;
    }

    @Override
    public Rule getRule() {
        return rule;
    }

    @Override
    public BranchNode getBranch() {
        return branch;
    }

    @NotNull
    @Override
    public ProcessStatus process(@NotNull Context context) throws Exception {
        if (getRule() == null) {
            throw new NullPointerException("rule can't be null");
        }
        if (branch == null) {
            return ProcessStatus.PROCEED;
        }

        Block whileBlock = new Block(true, true, context.getBlock());
        BlockWrapper blockWrapper = new BlockWrapper(whileBlock);
        ContextWrapper whileContext = Contexts.wrap(context, whileBlock);

        while (getRule().match(whileContext)) {
            ProcessStatus status = branch.execute(whileContext);
            if (ProcessStatus.isComplete(status)) {
                return status;
            }
            if (blockWrapper.hasBroken()) {
                break;
            }

            blockWrapper.resetBreak();
            blockWrapper.resetContinue();
        }

        return ProcessStatus.PROCEED;
    }
}

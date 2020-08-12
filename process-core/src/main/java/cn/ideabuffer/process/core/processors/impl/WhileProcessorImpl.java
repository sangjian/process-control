package cn.ideabuffer.process.core.processors.impl;

import cn.ideabuffer.process.core.block.InnerBlock;
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
        ContextWrapper whileContext = Contexts.wrap(context, whileBlock);

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

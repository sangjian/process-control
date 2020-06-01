package cn.ideabuffer.process.core.processors.impl;

import cn.ideabuffer.process.core.block.BlockFacade;
import cn.ideabuffer.process.core.block.InnerBlock;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.ContextWrapper;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.rule.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/05/02
 */
public class DoWhileProcessorImpl extends WhileProcessorImpl {

    public DoWhileProcessorImpl(Rule rule, BranchNode branch) {
        super(rule, branch);
    }

    @NotNull
    @Override
    public ProcessStatus process(@NotNull Context context) throws Exception {
        if (getRule() == null) {
            throw new NullPointerException("rule can't be null");
        }
        if (getBranch() == null) {
            return ProcessStatus.PROCEED;
        }

        InnerBlock whileBlock = new InnerBlock(true, true, context.getBlock());
        ContextWrapper whileContext = Contexts.wrap(context, whileBlock);

        do {
            whileBlock.resetBreak();
            whileBlock.resetContinue();
            ProcessStatus status = getBranch().execute(whileContext);
            if (ProcessStatus.isComplete(status)) {
                return status;
            }
            if (whileBlock.hasContinued()) {
                continue;
            }
            if (whileBlock.hasBroken()) {
                break;
            }
        } while (getRule().match(whileContext));

        return ProcessStatus.PROCEED;
    }
}

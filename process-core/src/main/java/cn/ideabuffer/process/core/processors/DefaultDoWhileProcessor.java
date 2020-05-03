package cn.ideabuffer.process.core.processors;

import cn.ideabuffer.process.core.block.Block;
import cn.ideabuffer.process.core.block.BlockWrapper;
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
public class DefaultDoWhileProcessor extends DefaultWhileProcessor {

    public DefaultDoWhileProcessor(Rule rule, BranchNode branch) {
        super(rule, branch);
    }

    @Override
    public ProcessStatus process(@NotNull Context context) throws Exception {
        if (getRule() == null) {
            throw new NullPointerException("rule can't be null");
        }
        if (getBranch() == null) {
            return ProcessStatus.PROCEED;
        }

        Block whileBlock = new Block(true, true, context.getBlock());
        BlockWrapper blockWrapper = new BlockWrapper(whileBlock);
        ContextWrapper whileContext = Contexts.wrap(context, whileBlock);

        do {
            blockWrapper.resetBreak();
            blockWrapper.resetContinue();
            ProcessStatus status = getBranch().execute(whileContext);
            if (ProcessStatus.isComplete(status)) {
                return status;
            }
            if (blockWrapper.hasContinued()) {
                continue;
            }
            if (blockWrapper.hasBroken()) {
                break;
            }
        } while (getRule().match(whileContext));

        return ProcessStatus.PROCEED;
    }
}

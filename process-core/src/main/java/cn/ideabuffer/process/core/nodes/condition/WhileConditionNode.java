package cn.ideabuffer.process.core.nodes.condition;

import cn.ideabuffer.process.core.block.Block;
import cn.ideabuffer.process.core.block.BlockWrapper;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.ContextWrapper;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.rule.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public class WhileConditionNode extends AbstractExecutableNode {

    protected BranchNode branch;

    public WhileConditionNode(@NotNull Rule rule, @NotNull BranchNode branch) {
        super(rule);
        this.branch = branch;
    }

    public void setBranch(BranchNode branch) {
        this.branch = branch;
    }

    @Override
    public WhileConditionNode parallel() {
        super.parallel();
        return this;
    }

    @Override
    public WhileConditionNode parallel(Executor executor) {
        super.parallel(executor);
        return this;
    }

    @Override
    protected boolean ruleCheck(Context context) {
        return true;
    }

    @NotNull
    @Override
    public ProcessStatus execute(Context context) throws Exception {
        if (getRule() == null) {
            throw new NullPointerException("rule can't be null");
        }
        return super.execute(context);
    }

    @NotNull
    @Override
    protected ProcessStatus doExecute(Context context) throws Exception {
        if (branch == null) {
            return ProcessStatus.PROCEED;
        }

        Block whileBlock = new Block(true, true, context.getBlock());
        BlockWrapper blockWrapper = new BlockWrapper(whileBlock);
        ContextWrapper whileContext = Contexts.wrap(context, whileBlock);

        while (true) {
            if (!getRule().match(whileContext)) {
                return ProcessStatus.PROCEED;
            }
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

    @Override
    protected void onDestroy() {
        try {
            if (branch != null) {
                branch.destroy();
            }
        } catch (Exception e) {
            logger.error("destroy encountered problem!", e);
            throw e;
        }

    }
}

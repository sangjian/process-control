package cn.ideabuffer.process.nodes.condition;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.ContextWrapper;
import cn.ideabuffer.process.block.Block;
import cn.ideabuffer.process.block.BlockWrapper;
import cn.ideabuffer.process.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.nodes.branch.BranchNode;
import cn.ideabuffer.process.rule.Rule;
import cn.ideabuffer.process.status.ProcessStatus;
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

    @Override
    public ProcessStatus execute(Context context) throws Exception {
        if (getRule() == null) {
            throw new NullPointerException("rule can't be null");
        }
        return super.execute(context);
    }

    @Override
    protected ProcessStatus doExecute(Context context) throws Exception {
        if (branch == null) {
            return ProcessStatus.PROCEED;
        }

        Block whileBlock = new Block(true, true, context.getBlock());
        BlockWrapper blockWrapper = new BlockWrapper(whileBlock);
        ContextWrapper whileContext = new ContextWrapper(context, whileBlock);
        if (!getRule().match(whileContext)) {
            return ProcessStatus.PROCEED;
        }
        while (true) {
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

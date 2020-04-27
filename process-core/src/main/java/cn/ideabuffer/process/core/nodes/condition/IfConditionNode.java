package cn.ideabuffer.process.core.nodes.condition;

import cn.ideabuffer.process.core.block.Block;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.ContextWrapper;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.rule.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public class IfConditionNode extends AbstractExecutableNode {

    private BranchNode trueBranch;

    private BranchNode falseBranch;

    public IfConditionNode(@NotNull Rule rule, @NotNull BranchNode trueBranch) {
        super(rule);
        this.trueBranch = trueBranch;
    }

    public IfConditionNode(@NotNull Rule rule, @NotNull BranchNode trueBranch, BranchNode falseBranch) {
        super(rule);
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
    }

    public void setTrueBranch(BranchNode trueBranch) {
        this.trueBranch = trueBranch;
    }

    public void setFalseBranch(BranchNode falseBranch) {
        this.falseBranch = falseBranch;
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

        Block ifBlock = new Block(context.getBlock());
        ContextWrapper contextWrapper = Contexts.wrap(context, ifBlock);
        BranchNode branch = getRule().match(contextWrapper) ? trueBranch : falseBranch;
        if (branch == null) {
            return ProcessStatus.PROCEED;
        }
        return branch.execute(contextWrapper);
    }

    @Override
    protected void onDestroy() {
        try {
            if (trueBranch != null) {
                trueBranch.destroy();
            }
            if (falseBranch != null) {
                falseBranch.destroy();
            }
        } catch (Exception e) {
            logger.error("destroy encountered problem!", e);
            throw e;
        }

    }
}

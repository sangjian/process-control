package cn.ideabuffer.process.core.processors;

import cn.ideabuffer.process.core.block.Block;
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
public class DefaultIfProcessor implements IfProcessor {

    private Rule rule;

    private BranchNode trueBranch;

    private BranchNode falseBranch;

    public DefaultIfProcessor(@NotNull Rule rule, @NotNull BranchNode trueBranch) {
        this(rule, trueBranch, null);
    }

    public DefaultIfProcessor(@NotNull Rule rule, @NotNull BranchNode trueBranch, BranchNode falseBranch) {
        this.rule = rule;
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public void setTrueBranch(BranchNode trueBranch) {
        this.trueBranch = trueBranch;
    }

    public void setFalseBranch(BranchNode falseBranch) {
        this.falseBranch = falseBranch;
    }

    @Override
    public Rule getRule() {
        return this.rule;
    }

    @Override
    public BranchNode getTrueBranch() {
        return this.trueBranch;
    }

    @Override
    public BranchNode getFalseBranch() {
        return this.falseBranch;
    }

    @Override
    public ProcessStatus process(@NotNull Context context) throws Exception {
        if (rule == null) {
            throw new NullPointerException("rule can't be null");
        }
        Block ifBlock = new Block(context.getBlock());
        ContextWrapper contextWrapper = Contexts.wrap(context, ifBlock);
        BranchNode branch = rule.match(contextWrapper) ? trueBranch : falseBranch;
        if (branch == null) {
            return ProcessStatus.PROCEED;
        }
        return branch.execute(contextWrapper);
    }
}

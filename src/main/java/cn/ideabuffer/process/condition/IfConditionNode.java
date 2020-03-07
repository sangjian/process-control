package cn.ideabuffer.process.condition;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.ContextWrapper;
import cn.ideabuffer.process.block.Block;
import cn.ideabuffer.process.branch.BranchNode;
import cn.ideabuffer.process.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.rule.Rule;

import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public class IfConditionNode extends AbstractExecutableNode {

    private BranchNode trueBranch;

    private BranchNode falseBranch;

    public IfConditionNode(Rule rule, BranchNode trueBranch) {
        super(rule);
        this.trueBranch = trueBranch;
    }

    public IfConditionNode(Rule rule, BranchNode trueBranch, BranchNode falseBranch) {
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
    public IfConditionNode parallel() {
        super.parallel();
        return this;
    }

    @Override
    public IfConditionNode parallel(Executor executor) {
        super.parallel(executor);
        return this;
    }

    @Override
    protected boolean ruleCheck(Context context) {
        return true;
    }

    @Override
    public boolean execute(Context context) throws Exception {
        if(rule == null) {
            throw new RuntimeException("rule can't be null");
        }
        return super.execute(context);
    }

    @Override
    protected boolean doExecute(Context context) throws Exception {

        Block ifBlock = new Block(context.getBlock());
        ContextWrapper contextWrapper = new ContextWrapper(context, ifBlock);
        BranchNode branch = rule.match(contextWrapper) ? trueBranch : falseBranch;
        if(branch == null ) {
            return false;
        }
        return branch.execute(contextWrapper);
    }

}

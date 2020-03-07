package cn.ideabuffer.process.condition;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.ContextWrapper;
import cn.ideabuffer.process.block.Block;
import cn.ideabuffer.process.branch.Branch;
import cn.ideabuffer.process.branch.BranchNode;
import cn.ideabuffer.process.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.rule.Rule;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public class IfConditionNode extends AbstractExecutableNode implements BranchNode {

    private Branch trueBranch;

    private Branch falseBranch;

    public IfConditionNode(Rule rule, Branch trueBranch) {
        super(rule);
        this.trueBranch = trueBranch;
    }

    public IfConditionNode(Rule rule, Branch trueBranch, Branch falseBranch) {
        super(rule);
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
    }

    public void setTrueBranch(Branch trueBranch) {
        this.trueBranch = trueBranch;
    }

    public void setFalseBranch(Branch falseBranch) {
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
        Branch branch = rule.match(contextWrapper) ? trueBranch : falseBranch;
        if(branch == null ) {
            return false;
        }
        return branch.execute(contextWrapper);
    }

}

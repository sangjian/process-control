package cn.ideabuffer.process.condition;

import cn.ideabuffer.process.branch.Branch;
import cn.ideabuffer.process.branch.BranchNode;
import cn.ideabuffer.process.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.ContextWrapper;
import cn.ideabuffer.process.block.Block;
import cn.ideabuffer.process.block.BlockWrapper;
import cn.ideabuffer.process.rule.Rule;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public class WhileConditionNode extends AbstractExecutableNode implements BranchNode {

    protected Rule rule;

    protected Branch branch;

    public WhileConditionNode(Rule rule, Branch branch) {
        this.rule = rule;
        this.branch = branch;
    }

    public void setBranch(Branch branch) {
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
    public boolean execute(Context context) throws Exception {
        if(rule == null) {
            throw new RuntimeException("rule can't be null");
        }
        return super.execute(context);
    }

    @Override
    protected boolean doExecute(Context context) throws Exception {
        if(branch == null) {
            return false;
        }

        Block whileBlock = new Block(true, true, context.getBlock());
        BlockWrapper blockWrapper = new BlockWrapper(whileBlock);
        ContextWrapper whileContext = new ContextWrapper(context, whileBlock);
        while (true) {
            blockWrapper.resetBreak();
            blockWrapper.resetContinue();

            if (!rule.match(whileContext)) {
                break;
            }
            if(branch.execute(whileContext)) {
                return true;
            }
            if (blockWrapper.hasBroken()) {
                break;
            }
        }

        return false;
    }
}

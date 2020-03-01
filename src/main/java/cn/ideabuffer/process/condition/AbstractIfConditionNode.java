package cn.ideabuffer.process.condition;

import cn.ideabuffer.process.block.BlockWrapper;
import cn.ideabuffer.process.branch.Branch;
import cn.ideabuffer.process.executor.ExecuteStrategy;
import cn.ideabuffer.process.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.ContextWrapper;
import cn.ideabuffer.process.ExecutableNode;
import cn.ideabuffer.process.block.Block;
import cn.ideabuffer.process.branch.DefaultBranch;

import static cn.ideabuffer.process.executor.ExecuteStrategies.SERIAL;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public abstract class AbstractIfConditionNode extends AbstractExecutableNode implements IfConditionNode {

    private Branch trueBranch;

    private Branch falseBranch;

    public AbstractIfConditionNode(String id) {
        this(null, null, null);
    }

    public AbstractIfConditionNode(String id, Branch trueBranch, Branch falseBranch) {
        super(id);
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
    public IfConditionNode trueBranch(Branch branch) {
        this.trueBranch = branch;
        return this;
    }

    @Override
    public IfConditionNode falseBranch(Branch branch) {
        this.falseBranch = branch;
        return this;
    }

    @Override
    public IfConditionNode trueBranch(ExecutableNode... nodes) {
        if(trueBranch == null) {
            trueBranch = new DefaultBranch();
        }
        trueBranch.addNodes(nodes);
        return this;
    }

    @Override
    public IfConditionNode falseBranch(ExecutableNode... nodes) {
        if(falseBranch == null) {
            falseBranch = new DefaultBranch();
        }
        falseBranch.addNodes(nodes);
        return this;
    }

    @Override
    public Branch getTrueBranch() {
        return trueBranch;
    }

    @Override
    public Branch getFalseBranch() {
        return falseBranch;
    }

    @Override
    protected boolean doExecute(Context context) throws Exception {
        Boolean judgement = judge(context);
        Branch branch = Boolean.TRUE.equals(judgement) ? trueBranch : falseBranch;
        if(branch == null || branch.getNodes() == null || branch.getNodes().size() == 0) {
            return false;
        }
        Block ifBlock = new Block(context.getBlock());
        ContextWrapper contextWrapper = new ContextWrapper(context, ifBlock);
        if(branch.execute(contextWrapper)) {
            return true;
        }
        return false;
    }
}

package cn.ideabuffer.process.condition;

import cn.ideabuffer.process.branch.Branch;
import cn.ideabuffer.process.branch.DefaultBranch;
import cn.ideabuffer.process.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.ContextWrapper;
import cn.ideabuffer.process.ExecutableNode;
import cn.ideabuffer.process.block.Block;
import cn.ideabuffer.process.block.BlockWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static cn.ideabuffer.process.executor.ExecuteStrategies.SERIAL;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public abstract class AbstractWhileConditionNode extends AbstractExecutableNode implements WhileConditionNode {

    private Branch branch;

    public AbstractWhileConditionNode(String id) {
        super(id);
        branch = new DefaultBranch() {};
    }

    public AbstractWhileConditionNode(Branch branch) {
        this.branch = branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    @Override
    public WhileConditionNode addNode(ExecutableNode... nodes) {
        if(nodes == null || nodes.length == 0) {
            return this;
        }
        if (branch == null) {
            branch = new DefaultBranch();
        }
        branch.addNodes(nodes);
        return this;
    }

    @Override
    public Branch getBranch() {
        return branch;
    }

    @Override
    protected boolean doExecute(Context context) throws Exception {
        if(branch == null) {
            return false;
        }
        List<ExecutableNode> list = branch.getNodes();
        if (list == null || list.isEmpty()) {
            return false;
        }

        Block whileBlock = new Block(true, true, context.getBlock());
        BlockWrapper blockWrapper = new BlockWrapper(whileBlock);
        ContextWrapper whileContext = new ContextWrapper(context, whileBlock);
        while (true) {
            blockWrapper.resetBreak();
            blockWrapper.resetContinue();
            Boolean judgement = judge(whileContext);
            if (!Boolean.TRUE.equals(judgement)) {
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

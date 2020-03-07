package cn.ideabuffer.process.nodes;

import cn.ideabuffer.process.branch.Branch;
import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.ExecutableNode;
import cn.ideabuffer.process.ParallelBranchNode;
import cn.ideabuffer.process.branch.DefaultBranch;
import cn.ideabuffer.process.executor.NodeExecutors;
import cn.ideabuffer.process.executor.ProceedStrategy;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/03/01
 */
public class DefaultParallelBranchNode extends AbstractExecutableNode implements ParallelBranchNode {

    private List<Branch> branches;

    private ProceedStrategy strategy = null;

    public DefaultParallelBranchNode() {
        this(null);
    }

    public DefaultParallelBranchNode(List<Branch> branches) {
        this.branches = branches == null ? new ArrayList<>() : branches;
    }

    public void setBranches(@NotNull List<Branch> branches) {
        this.branches = branches;
    }

    public void setStrategy(@NotNull ProceedStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public ParallelBranchNode proceedWhen(@NotNull ProceedStrategy strategy) {
        this.strategy = strategy;
        return this;
    }

    @Override
    public boolean execute(Context context) throws Exception {
        if(branches == null) {
            return false;
        }
        return doExecute(context);
    }

    @Override
    protected boolean doExecute(Context context) throws Exception {
        return NodeExecutors.PARALLEL_EXECUTOR.execute(executor, strategy, context, branches.toArray(new ExecutableNode[0]));
    }

    @Override
    public ParallelBranchNode addBranch(@NotNull ExecutableNode... nodes) {
        if(nodes.length > 0) {
            branches.add(new DefaultBranch(nodes));
        }
        return this;
    }

    @Override
    public ParallelBranchNode addBranch(@NotNull Branch branch) {
        this.branches.add(branch);
        return this;
    }
}

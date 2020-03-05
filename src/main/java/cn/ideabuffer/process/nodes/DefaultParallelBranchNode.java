package cn.ideabuffer.process.nodes;

import cn.ideabuffer.process.branch.Branch;
import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.ExecutableNode;
import cn.ideabuffer.process.ParallelBranchNode;
import cn.ideabuffer.process.branch.DefaultBranch;
import cn.ideabuffer.process.executor.ExecuteStrategy;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/03/01
 */
public class DefaultParallelBranchNode extends AbstractExecutableNode implements ParallelBranchNode {

    private List<Branch> branches;

    private ExecuteStrategy strategy;

    public DefaultParallelBranchNode() {
        this(null);
    }

    public DefaultParallelBranchNode(List<Branch> branches) {
        this(null, branches);
    }

    public DefaultParallelBranchNode(String id, List<Branch> branches) {
        super(id);
        this.branches = branches;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }

    public void setStrategy(ExecuteStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public ParallelBranchNode proceedWhen(ExecuteStrategy strategy) {
        this.strategy = strategy;
        return this;
    }

    @Override
    public boolean execute(Context context) throws Exception {
        return doExecute(context);
    }

    @Override
    protected boolean doExecute(Context context) throws Exception {
        ExecutableNode[] nodes = new ExecutableNode[branches.size()];

        return this.strategy.execute(getExecutor(), context, branches.toArray(nodes));
    }

    @Override
    public ParallelBranchNode addBranch(ExecutableNode... nodes) {
        if(nodes != null && nodes.length > 0) {
            branches.add(new DefaultBranch(nodes));
        }
        return this;
    }

    @Override
    public ParallelBranchNode addBranch(Branch branch) {
        if(branch != null) {
            this.branches.add(branch);
        }
        return this;
    }
}

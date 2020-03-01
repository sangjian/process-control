package cn.ideabuffer.process.nodes;

import cn.ideabuffer.process.branch.Branch;
import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.ExecutableNode;
import cn.ideabuffer.process.ParallelBranchNode;
import cn.ideabuffer.process.branch.DefaultBranch;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/03/01
 */
public class DefaultParallelBranchNode extends AbstractExecutableNode implements ParallelBranchNode {

    private List<Branch> branches;


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

    @Override
    public Boolean judge(Context context) {
        return true;
    }

    @Override
    public boolean execute(Context context) throws Exception {
        return doExecute(context);
    }

    @Override
    protected boolean doExecute(Context context) throws Exception {
        ExecutableNode[] nodes = new ExecutableNode[branches.size()];

        return getExecuteStrategy().execute(getExecutor(), context, branches.toArray(nodes));
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

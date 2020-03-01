package cn.ideabuffer.process;

import cn.ideabuffer.process.branch.Branch;
import cn.ideabuffer.process.branch.BranchNode;

/**
 * @author sangjian.sj
 * @date 2020/03/01
 */
public interface ParallelBranchNode extends BranchNode<Boolean> {

    ParallelBranchNode addBranch(ExecutableNode... nodes);

    ParallelBranchNode addBranch(Branch branch);
}

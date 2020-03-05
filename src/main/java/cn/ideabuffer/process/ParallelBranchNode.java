package cn.ideabuffer.process;

import cn.ideabuffer.process.branch.Branch;
import cn.ideabuffer.process.branch.BranchNode;
import cn.ideabuffer.process.executor.ExecuteStrategy;

/**
 * @author sangjian.sj
 * @date 2020/03/01
 */
public interface ParallelBranchNode extends BranchNode {

    ParallelBranchNode addBranch(ExecutableNode... nodes);

    ParallelBranchNode addBranch(Branch branch);

    ParallelBranchNode proceedWhen(ExecuteStrategy strategy);
}

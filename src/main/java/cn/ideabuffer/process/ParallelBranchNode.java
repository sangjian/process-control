package cn.ideabuffer.process;

import cn.ideabuffer.process.branch.Branch;
import cn.ideabuffer.process.branch.BranchNode;
import cn.ideabuffer.process.executor.ExecuteStrategy;
import cn.ideabuffer.process.executor.ProceedStrategy;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/03/01
 */
public interface ParallelBranchNode extends BranchNode {

    ParallelBranchNode addBranch(@NotNull ExecutableNode... nodes);

    ParallelBranchNode addBranch(@NotNull Branch branch);

    ParallelBranchNode proceedWhen(@NotNull ProceedStrategy strategy);
}

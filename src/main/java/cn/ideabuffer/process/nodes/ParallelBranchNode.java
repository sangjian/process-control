package cn.ideabuffer.process.nodes;

import cn.ideabuffer.process.nodes.branch.BranchNode;
import cn.ideabuffer.process.executor.ProceedStrategy;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/03/01
 */
public interface ParallelBranchNode {

    ParallelBranchNode addBranch(@NotNull ExecutableNode... nodes);

    ParallelBranchNode addBranch(@NotNull BranchNode branch);

    ParallelBranchNode proceedWhen(@NotNull ProceedStrategy strategy);
}

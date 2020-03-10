package cn.ideabuffer.process.nodes;

import cn.ideabuffer.process.nodes.branch.BranchNode;
import cn.ideabuffer.process.rule.Rule;
import cn.ideabuffer.process.strategy.ProceedStrategy;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/03/01
 */
public interface ParallelBranchNode extends ExecutableNode {
    @Override
    ExecutableNode processOn(Rule rule);

    @Override
    ExecutableNode parallel();

    @Override
    ExecutableNode parallel(Executor executor);

    ParallelBranchNode addBranch(@NotNull ExecutableNode... nodes);

    ParallelBranchNode addBranch(@NotNull BranchNode branch);

    ParallelBranchNode proceedWhen(@NotNull ProceedStrategy strategy);
}

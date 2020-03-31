package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.strategy.ProceedStrategy;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.rule.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;

/**
 * 可并行执行的分支节点
 *
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

    /**
     * 添加分支，根据传入的节点列表创建一个新分支，并添加到当前分支节点
     *
     * @param nodes 节点列表
     * @return 当前节点
     */
    ParallelBranchNode addBranch(@NotNull ExecutableNode... nodes);

    /**
     * 添加分支到当前节点
     *
     * @param branch
     * @return
     */
    ParallelBranchNode addBranch(@NotNull BranchNode branch);

    ParallelBranchNode proceedWhen(@NotNull ProceedStrategy strategy);
}

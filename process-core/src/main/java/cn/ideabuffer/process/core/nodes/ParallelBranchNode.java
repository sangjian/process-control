package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.strategy.ProceedStrategy;
import org.jetbrains.annotations.NotNull;

/**
 * 可并行执行的分支节点
 *
 * @author sangjian.sj
 * @date 2020/03/01
 */
public interface ParallelBranchNode extends ExecutableNode<Void> {

    /**
     * 添加分支，根据传入的节点列表创建一个新分支，并添加到当前分支节点
     *
     * @param nodes 节点列表
     * @return 当前节点
     */
    void addBranch(@NotNull ExecutableNode<?>... nodes);

    /**
     * 添加分支到当前节点
     *
     * @param branch
     * @return
     */
    void addBranch(@NotNull BranchNode branch);

    void proceedWhen(@NotNull ProceedStrategy strategy);
}

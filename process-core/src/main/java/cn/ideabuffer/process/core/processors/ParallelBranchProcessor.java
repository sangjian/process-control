package cn.ideabuffer.process.core.processors;

import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.status.ProcessStatus;
import cn.ideabuffer.process.core.strategy.ProceedStrategy;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/05/02
 */
public interface ParallelBranchProcessor extends ComplexProcessor<ProcessStatus> {

    /**
     * 添加分支，根据传入的节点列表创建一个新分支，并添加到当前分支节点
     *
     * @param nodes 节点列表
     * @return 当前节点
     */
    void addBranch(@NotNull ExecutableNode<?, ?>... nodes);

    /**
     * 添加分支到当前节点
     *
     * @param branch
     * @return
     */
    void addBranch(@NotNull BranchNode branch);

    void proceedWhen(@NotNull ProceedStrategy strategy);

}

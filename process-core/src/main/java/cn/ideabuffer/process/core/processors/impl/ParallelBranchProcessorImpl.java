package cn.ideabuffer.process.core.processors.impl;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.executor.NodeExecutors;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.nodes.branch.DefaultBranchNode;
import cn.ideabuffer.process.core.processors.ParallelBranchProcessor;
import cn.ideabuffer.process.core.status.ProcessStatus;
import cn.ideabuffer.process.core.strategy.ProceedStrategies;
import cn.ideabuffer.process.core.strategy.ProceedStrategy;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/05/02
 */
public class ParallelBranchProcessorImpl implements ParallelBranchProcessor {

    private List<BranchNode> branches;

    private ProceedStrategy strategy = ProceedStrategies.AT_LEAST_ONE_FINISHED;

    private Executor executor;

    public ParallelBranchProcessorImpl() {
        this(null, null);
    }

    public ParallelBranchProcessorImpl(List<BranchNode> branches, Executor executor) {
        this.executor = executor;
        this.branches = branches == null ? new ArrayList<>() : branches;
    }

    @Override
    public void addBranch(@NotNull ExecutableNode<?, ?>... nodes) {
        this.branches.add(new DefaultBranchNode(nodes));
    }

    @Override
    public void addBranch(@NotNull BranchNode branch) {
        this.branches.add(branch);
    }

    @Override
    public void parallelBy(Executor executor) {
        this.executor = executor;
    }

    @Override
    public void proceedWhen(@NotNull ProceedStrategy strategy) {
        this.strategy = strategy;
    }

    @NotNull
    @Override
    public ProcessStatus process(@NotNull Context context) throws Exception {
        return NodeExecutors.PARALLEL_EXECUTOR.execute(executor, strategy, context,
            branches.toArray(new ExecutableNode[0]));
    }
}

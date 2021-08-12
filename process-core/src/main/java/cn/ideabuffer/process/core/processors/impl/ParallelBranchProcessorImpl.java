package cn.ideabuffer.process.core.processors.impl;

import cn.ideabuffer.process.core.LifecycleManager;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.executors.NodeExecutors;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.nodes.branch.DefaultBranchNode;
import cn.ideabuffer.process.core.processors.ParallelBranchProcessor;
import cn.ideabuffer.process.core.status.ProcessStatus;
import cn.ideabuffer.process.core.strategies.ProceedStrategies;
import cn.ideabuffer.process.core.strategies.ProceedStrategy;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 * @author sangjian.sj
 * @date 2020/05/02
 */
public class ParallelBranchProcessorImpl implements ParallelBranchProcessor {

    private List<BranchNode> branches;

    private ProceedStrategy strategy = ProceedStrategies.ALL_PROCEEDED;

    private Executor executor;

    public ParallelBranchProcessorImpl() {
        this(null, null);
    }

    public ParallelBranchProcessorImpl(List<BranchNode> branches, Executor executor) {
        this.executor = executor;
        this.branches = branches == null ? new LinkedList<>() : branches;
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

    @Override
    public List<BranchNode> getBranches() {
        return Collections.unmodifiableList(this.branches);
    }

    @NotNull
    @Override
    public ProcessStatus process(@NotNull Context context) throws Exception {
        return NodeExecutors.PARALLEL_EXECUTOR.execute(executor, strategy, context,
            branches.toArray(new ExecutableNode[0]));
    }

    @Override
    public void initialize() {
        if (branches == null || branches.isEmpty()) {
            return;
        }
        LifecycleManager.initialize(branches);
    }

    @Override
    public void destroy() {
        if (executor instanceof ExecutorService && !((ExecutorService)executor).isShutdown()) {
            ((ExecutorService)executor).shutdown();
        }
        if (branches != null && !branches.isEmpty()) {
            LifecycleManager.destroy(branches);
        }
    }
}

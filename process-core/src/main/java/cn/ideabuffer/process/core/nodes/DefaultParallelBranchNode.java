package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.executor.NodeExecutors;
import cn.ideabuffer.process.core.handler.ExceptionHandler;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.nodes.branch.DefaultBranchNode;
import cn.ideabuffer.process.core.rule.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;
import cn.ideabuffer.process.core.strategy.ProceedStrategies;
import cn.ideabuffer.process.core.strategy.ProceedStrategy;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/03/01
 */
public class DefaultParallelBranchNode extends AbstractExecutableNode implements ParallelBranchNode {

    private List<BranchNode> branches;

    private ProceedStrategy strategy = ProceedStrategies.AT_LEAST_ONE_FINISHED;

    public DefaultParallelBranchNode() {
        this(false);
    }

    public DefaultParallelBranchNode(boolean parallel) {
        this(parallel, null);
    }

    public DefaultParallelBranchNode(Rule rule) {
        this(false, rule, null, null);
    }

    public DefaultParallelBranchNode(boolean parallel, Executor executor) {
        this(parallel, null, executor, null);
    }

    public DefaultParallelBranchNode(List<BranchNode> branches) {
        this(false, null, null, null, branches);
    }

    public DefaultParallelBranchNode(boolean parallel, Rule rule,
        Executor executor, ExceptionHandler handler) {
        this(parallel, rule, executor, handler, null);
    }

    public DefaultParallelBranchNode(boolean parallel, Rule rule,
        Executor executor, ExceptionHandler handler, List<BranchNode> branches) {
        super(parallel, rule, executor, handler);
        this.branches = branches == null ? new ArrayList<>() : branches;
    }

    public void setBranches(@NotNull List<BranchNode> branches) {
        this.branches = branches;
    }

    public void setStrategy(@NotNull ProceedStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public ParallelBranchNode proceedWhen(@NotNull ProceedStrategy strategy) {
        this.strategy = strategy;
        return this;
    }

    @Override
    public ProcessStatus execute(Context context) throws Exception {
        if (branches == null || !ruleCheck(context)) {
            return ProcessStatus.PROCEED;
        }
        return doExecute(context);
    }

    @Override
    protected ProcessStatus doExecute(Context context) throws Exception {
        return NodeExecutors.PARALLEL_EXECUTOR.execute(getExecutor(), strategy, context, branches.toArray(new ExecutableNode[0]));
    }

    @Override
    public ParallelBranchNode addBranch(@NotNull ExecutableNode... nodes) {
        if (nodes.length > 0) {
            branches.add(new DefaultBranchNode(nodes));
        }
        return this;
    }

    @Override
    public ParallelBranchNode addBranch(@NotNull BranchNode branch) {
        this.branches.add(branch);
        return this;
    }
}

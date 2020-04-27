package cn.ideabuffer.process.core.nodes.builder;

import cn.ideabuffer.process.core.handler.ExceptionHandler;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.nodes.Nodes;
import cn.ideabuffer.process.core.nodes.ParallelBranchNode;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.nodes.branch.Branches;
import cn.ideabuffer.process.core.rule.Rule;
import cn.ideabuffer.process.core.strategy.ProceedStrategy;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/04/24
 */
public class ParallelBranchNodeBuilder extends AbstractExecutableNodeBuilder<ParallelBranchNode> {

    private List<BranchNode> branches;

    private ProceedStrategy strategy;

    private ParallelBranchNodeBuilder(ParallelBranchNode node) {
        super(node);
        this.branches = new ArrayList<>();
    }

    public static ParallelBranchNodeBuilder newBuilder() {
        return new ParallelBranchNodeBuilder(Nodes.newParallelBranchNode());
    }

    @Override
    public ParallelBranchNodeBuilder exceptionHandler(ExceptionHandler handler) {
        super.exceptionHandler(handler);
        return this;
    }

    @Override
    public ParallelBranchNodeBuilder parallel(Executor executor) {
        super.parallel(executor);
        return this;
    }

    @Override
    public ParallelBranchNodeBuilder processOn(Rule rule) {
        super.processOn(rule);
        return this;
    }

    public ParallelBranchNodeBuilder addBranch(@NotNull ExecutableNode... nodes) {
        this.branches.add(Branches.newBranch(nodes));
        return this;
    }

    public ParallelBranchNodeBuilder addBranch(@NotNull BranchNode branch) {
        this.branches.add(branch);
        return this;
    }

    public ParallelBranchNodeBuilder proceedWhen(@NotNull ProceedStrategy strategy) {
        this.strategy = strategy;
        return this;
    }

    @Override
    public ParallelBranchNode build() {
        ParallelBranchNode node = super.build();
        this.branches.forEach(node::addBranch);
        node.proceedWhen(strategy);
        return node;
    }
}

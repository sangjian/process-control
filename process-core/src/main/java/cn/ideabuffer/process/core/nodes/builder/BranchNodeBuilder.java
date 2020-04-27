package cn.ideabuffer.process.core.nodes.builder;

import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.nodes.branch.Branches;
import cn.ideabuffer.process.core.rule.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/04/24
 */
public class BranchNodeBuilder extends AbstractExecutableNodeBuilder<BranchNode> {

    private ExecutableNode[] nodes;

    private BranchNodeBuilder(BranchNode node) {
        super(node);
    }

    public static BranchNodeBuilder newBuilder() {
        return new BranchNodeBuilder(Branches.newBranch());
    }

    @Override
    public BranchNodeBuilder parallel() {
        super.parallel();
        return this;
    }

    @Override
    public BranchNodeBuilder parallel(Executor executor) {
        super.parallel(executor);
        return this;
    }

    @Override
    public BranchNodeBuilder processOn(Rule rule) {
        super.processOn(rule);
        return this;
    }

    public BranchNodeBuilder addNodes(@NotNull ExecutableNode... nodes) {
        this.nodes = nodes;
        return this;
    }

    @Override
    public BranchNode build() {
        BranchNode node = super.build();
        node.addNodes(nodes);
        return node;
    }
}

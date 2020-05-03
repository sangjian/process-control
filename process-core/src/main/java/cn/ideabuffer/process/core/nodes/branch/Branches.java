package cn.ideabuffer.process.core.nodes.branch;

import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.nodes.Nodes;
import cn.ideabuffer.process.core.processors.BranchProcessor;
import cn.ideabuffer.process.core.rule.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class Branches {

    private Branches() {
        throw new IllegalStateException("Utility class");
    }

    public static BranchNode newBranch() {
        return new DefaultBranchNode();
    }

    public static BranchNode newBranch(Rule rule, @NotNull Processor<?>... processors) {
        return newBranch(rule, processors);
    }

    public static BranchNode newBranch(@NotNull Processor<?>... processors) {
        List<ExecutableNode<?, ?>> nodes = Arrays.stream(processors).map(Nodes::newProcessNode).collect(Collectors.toList());
        return newBranch(null, nodes);
    }

    public static BranchNode newBranch(@NotNull List<ExecutableNode<?, ?>> nodes) {
        return newBranch(null, nodes);
    }

    public static BranchNode newBranch(Rule rule, @NotNull List<ExecutableNode<?, ?>> nodes) {
        return new DefaultBranchNode(rule, nodes);
    }

    public static BranchNode newBranch(@NotNull ExecutableNode<?, ?>... nodes) {
        return newBranch(null, Arrays.asList(nodes));
    }

    public static BranchNode newBranch(Rule rule, @NotNull ExecutableNode<?, ?>... nodes) {
        return newBranch(rule, Arrays.asList(nodes));
    }
}

package cn.ideabuffer.process.core.nodes.branch;

import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.rule.Rule;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class Branches {

    private Branches() {
        throw new IllegalStateException("Utility class");
    }

    public static BranchNode newBranch(@NotNull ExecutableNode... nodes) {
        return newBranch(null, nodes);
    }

    public static BranchNode newBranch(Rule rule, @NotNull ExecutableNode... nodes) {
        return new DefaultBranchNode(rule, nodes);
    }
}

package cn.ideabuffer.process.branch;

import cn.ideabuffer.process.ExecutableNode;
import cn.ideabuffer.process.rule.Rule;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class Branches {

    public static Branch newDefaultBranch(@NotNull ExecutableNode... nodes) {
        return newDefaultBranch(null, nodes);
    }

    public static Branch newDefaultBranch(Rule rule, @NotNull ExecutableNode... nodes) {
        return new DefaultBranch(rule, nodes);
    }
}

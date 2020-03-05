package cn.ideabuffer.process.branch;

import cn.ideabuffer.process.ExecutableNode;
import cn.ideabuffer.process.rule.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/03/01
 */
public interface Branch extends ExecutableNode {

    Branch addNodes(@NotNull ExecutableNode... nodes);

    List<ExecutableNode> getNodes();

    Branch processOn(Rule rule);

    Rule getRule();

}

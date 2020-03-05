package cn.ideabuffer.process.branch;

import cn.ideabuffer.process.ExecutableNode;
import cn.ideabuffer.process.rule.Rule;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/03/01
 */
public interface Branch extends ExecutableNode {

    Branch addNodes(ExecutableNode... nodes);

    List<ExecutableNode> getNodes();

    Branch processOn(Rule rule);

    Rule getRule();

}

package cn.ideabuffer.process.core.processors;

import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.rule.Rule;

/**
 * @author sangjian.sj
 * @date 2020/05/02
 */
public interface WhileProcessor extends StatusProcessor {

    Rule getRule();

    BranchNode getBranch();

}

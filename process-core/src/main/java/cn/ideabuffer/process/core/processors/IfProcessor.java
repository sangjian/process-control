package cn.ideabuffer.process.core.processors;

import cn.ideabuffer.process.core.KeyManager;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.rules.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;

/**
 * @author sangjian.sj
 * @date 2020/05/02
 */
public interface IfProcessor extends ComplexProcessor<ProcessStatus>, KeyManager {

    Rule getRule();

    void setRule(Rule rule);

    BranchNode getTrueBranch();

    void setTrueBranch(BranchNode trueBranch);

    BranchNode getFalseBranch();

    void setFalseBranch(BranchNode falseBranch);
}

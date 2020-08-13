package cn.ideabuffer.process.core.processors;

import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.context.KeyMapper;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.rule.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;

import java.util.Set;

/**
 * @author sangjian.sj
 * @date 2020/05/02
 */
public interface IfProcessor extends ComplexProcessor<ProcessStatus> {

    Rule getRule();

    BranchNode getTrueBranch();

    BranchNode getFalseBranch();

    KeyMapper getKeyMapper();

    void setKeyMapper(KeyMapper keyMapper);

    Set<Key<?>> getRequiredKeys();

    void setRequiredKeys(Set<Key<?>> requiredKeys);
}

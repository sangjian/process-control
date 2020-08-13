package cn.ideabuffer.process.core.nodes.condition;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.context.KeyMapper;
import cn.ideabuffer.process.core.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.processors.WhileProcessor;
import cn.ideabuffer.process.core.processors.impl.WhileProcessorImpl;
import cn.ideabuffer.process.core.rule.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public class WhileConditionNode extends AbstractExecutableNode<ProcessStatus, WhileProcessor> {

    public WhileConditionNode(@NotNull Rule rule, @NotNull BranchNode branch) {
        this(rule, branch, null, null);
    }

    public WhileConditionNode(@NotNull Rule rule, @NotNull BranchNode branch, KeyMapper keyMapper, Set<Key<?>> requiredKeys) {
        this(new WhileProcessorImpl(rule, branch, keyMapper, requiredKeys));
    }

    public WhileConditionNode(@NotNull WhileProcessor processor) {
        super.registerProcessor(processor);
    }

    @Override
    public KeyMapper getKeyMapper() {
        return getProcessor().getKeyMapper();
    }

    @Override
    public void setKeyMapper(KeyMapper mapper) {
        getProcessor().setKeyMapper(mapper);
    }

    @Override
    public void setRequiredKeys(Set<Key<?>> keys) {
        getProcessor().setRequiredKeys(keys);
    }

    @Override
    public Set<Key<?>> getRequiredKeys() {
        return getProcessor().getRequiredKeys();
    }

    @Override
    protected boolean ruleCheck(@NotNull Context context) {
        return true;
    }

    @Override
    protected void onDestroy() {
        try {
            if (getProcessor().getBranch() != null) {
                getProcessor().getBranch().destroy();
            }
        } catch (Exception e) {
            logger.error("destroy encountered problem!", e);
            throw e;
        }

    }
}

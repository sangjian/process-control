package cn.ideabuffer.process.core.nodes.condition;

import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.context.KeyMapper;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.processors.DoWhileProcessor;
import cn.ideabuffer.process.core.processors.impl.DoWhileProcessorImpl;
import cn.ideabuffer.process.core.rules.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public class DoWhileConditionNode extends WhileConditionNode {

    public DoWhileConditionNode(@NotNull Rule rule, @NotNull BranchNode branch) {
        this(new DoWhileProcessorImpl(rule, branch, null, null, null));
    }

    public DoWhileConditionNode(@NotNull Rule rule,
        @NotNull BranchNode branch, KeyMapper keyMapper, Set<Key<?>> readableKeys, Set<Key<?>> writableKeys) {
        this(new DoWhileProcessorImpl(rule, branch, keyMapper, readableKeys, writableKeys));
    }

    public DoWhileConditionNode(@NotNull DoWhileProcessor processor) {
        super(processor);
    }

}

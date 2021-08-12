package cn.ideabuffer.process.core.nodes.condition;

import cn.ideabuffer.process.core.ComplexNodes;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.context.KeyMapper;
import cn.ideabuffer.process.core.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.processors.WhileProcessor;
import cn.ideabuffer.process.core.processors.impl.WhileProcessorImpl;
import cn.ideabuffer.process.core.rules.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public class WhileConditionNode extends AbstractExecutableNode<ProcessStatus, WhileProcessor> implements ComplexNodes<ExecutableNode<?, ?>> {

    public WhileConditionNode() {
    }

    public WhileConditionNode(@NotNull Rule rule, @NotNull BranchNode branch) {
        this(rule, branch, null, null, null);
    }

    public WhileConditionNode(@NotNull Rule rule, @NotNull BranchNode branch, KeyMapper keyMapper,
        Set<Key<?>> readableKeys, Set<Key<?>> writableKeys) {
        setKeyMapper(keyMapper);
        setReadableKeys(readableKeys);
        setWritableKeys(writableKeys);
        registerProcessor(new WhileProcessorImpl(rule, branch, this));
    }

    public WhileConditionNode(@NotNull WhileProcessor processor) {
        super.registerProcessor(processor);
    }

    @Override
    protected boolean ruleCheck(@NotNull Context context) {
        return true;
    }

    @Override
    public List<ExecutableNode<?, ?>> getNodes() {
        List<ExecutableNode<?, ?>> nodes = new LinkedList<>();
        if (getProcessor().getBranch() != null) {
            nodes.add(getProcessor().getBranch());
            List<ExecutableNode<?, ?>> branchNodes = getProcessor().getBranch().getNodes();
            if (branchNodes != null) {
                nodes.addAll(branchNodes);
            }
        }
        return nodes;
    }
}

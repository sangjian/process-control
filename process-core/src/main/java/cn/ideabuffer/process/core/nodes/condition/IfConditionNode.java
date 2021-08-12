package cn.ideabuffer.process.core.nodes.condition;

import cn.ideabuffer.process.core.ComplexNodes;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.context.KeyMapper;
import cn.ideabuffer.process.core.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.processors.IfProcessor;
import cn.ideabuffer.process.core.processors.impl.IfProcessorImpl;
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
public class IfConditionNode extends AbstractExecutableNode<ProcessStatus, IfProcessor> implements ComplexNodes<ExecutableNode<?, ?>> {

    public IfConditionNode(@NotNull Rule rule, @NotNull BranchNode trueBranch) {
        this(rule, trueBranch, null);
    }

    public IfConditionNode(@NotNull Rule rule, @NotNull BranchNode trueBranch, BranchNode falseBranch) {
        this(rule, trueBranch, falseBranch, null, null, null);
    }

    public IfConditionNode(@NotNull Rule rule, @NotNull BranchNode trueBranch, BranchNode falseBranch,
        KeyMapper keyMapper, Set<Key<?>> readableKeys, Set<Key<?>> writableKeys) {
        setKeyMapper(keyMapper);
        setReadableKeys(readableKeys);
        setWritableKeys(writableKeys);
        registerProcessor(new IfProcessorImpl(rule, trueBranch, falseBranch, this));
    }

    public IfConditionNode() {

    }

    @Override
    protected boolean ruleCheck(@NotNull Context context) {
        return true;
    }

    @Override
    public List<ExecutableNode<?, ?>> getNodes() {
        List<ExecutableNode<?, ?>> nodes = new LinkedList<>();
        if (getProcessor().getTrueBranch() != null) {
            List<ExecutableNode<?, ?>> branchNodes = getProcessor().getTrueBranch().getNodes();
            if (branchNodes != null) {
                nodes.addAll(branchNodes);
            }
        }
        if (getProcessor().getFalseBranch() != null) {
            List<ExecutableNode<?, ?>> branchNodes = getProcessor().getFalseBranch().getNodes();
            if (branchNodes != null) {
                nodes.addAll(branchNodes);
            }
        }
        return nodes;
    }
}

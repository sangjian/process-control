package cn.ideabuffer.process.core.nodes.condition;

import cn.ideabuffer.process.core.ComplexNodes;
import cn.ideabuffer.process.core.KeyManager;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.processors.DoWhileProcessor;
import cn.ideabuffer.process.core.processors.impl.DoWhileProcessorImpl;
import cn.ideabuffer.process.core.rules.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public class DoWhileConditionNode extends AbstractExecutableNode<ProcessStatus, DoWhileProcessor> implements ComplexNodes<ExecutableNode<?, ?>> {

    public DoWhileConditionNode() {
    }

    public DoWhileConditionNode(@NotNull Rule rule, @NotNull BranchNode branch) {
        this(new DoWhileProcessorImpl(rule, branch, null));
    }

    public DoWhileConditionNode(@NotNull Rule rule,
                                @NotNull BranchNode branch, KeyManager keyManager) {
        registerProcessor(new DoWhileProcessorImpl(rule, branch, this));
    }

    public DoWhileConditionNode(@NotNull DoWhileProcessor processor) {
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

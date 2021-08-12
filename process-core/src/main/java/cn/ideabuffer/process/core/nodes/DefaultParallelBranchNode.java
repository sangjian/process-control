package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.processors.ParallelBranchProcessor;
import cn.ideabuffer.process.core.processors.impl.ParallelBranchProcessorImpl;
import cn.ideabuffer.process.core.rules.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/03/01
 */
public class DefaultParallelBranchNode extends AbstractExecutableNode<ProcessStatus, ParallelBranchProcessor>
    implements ParallelBranchNode {

    public DefaultParallelBranchNode() {
        this(null, null, null);
    }

    public DefaultParallelBranchNode(Rule rule, Executor executor, List<BranchNode> branches) {
        super.processOn(rule);
        super.registerProcessor(new ParallelBranchProcessorImpl(branches, executor));
    }

    @NotNull
    @Override
    public ProcessStatus execute(Context context) throws Exception {
        if (!enabled()) {
            return ProcessStatus.proceed();
        }
        Context ctx = Contexts.wrap(context, context.getBlock(), this);
        if (getProcessor() == null || !ruleCheck(ctx)) {
            return ProcessStatus.proceed();
        }
        return getProcessor().process(ctx);
    }

    @Override
    public List<ExecutableNode<?, ?>> getNodes() {
        List<ExecutableNode<?, ?>> nodes = new LinkedList<>();
        if (getProcessor() == null || getProcessor().getBranches() == null) {
            return nodes;
        }
        for (BranchNode branch : getProcessor().getBranches()) {
            nodes.add(branch);
            List<ExecutableNode<?, ?>> branchNodes = branch.getNodes();
            if (branchNodes != null) {
                nodes.addAll(branchNodes);
            }
        }
        return nodes;
    }
}

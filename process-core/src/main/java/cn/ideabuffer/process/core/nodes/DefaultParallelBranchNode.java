package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.processors.ParallelBranchProcessor;
import cn.ideabuffer.process.core.processors.impl.ParallelBranchProcessorImpl;
import cn.ideabuffer.process.core.rule.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

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
        Context ctx = Contexts.wrap(context, context.getBlock(), getKeyMapper(), getRequiredKeys());
        if (getProcessor() == null || !ruleCheck(ctx)) {
            return ProcessStatus.proceed();
        }
        return getProcessor().process(ctx);
    }

}

package cn.ideabuffer.process.core.nodes.condition;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.processors.impl.WhileProcessorImpl;
import cn.ideabuffer.process.core.processors.WhileProcessor;
import cn.ideabuffer.process.core.rule.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public class WhileConditionNode extends AbstractExecutableNode<ProcessStatus, WhileProcessor> {


    public WhileConditionNode(@NotNull Rule rule, @NotNull BranchNode branch) {
        this(new WhileProcessorImpl(rule, branch));
    }

    public WhileConditionNode(@NotNull WhileProcessor processor) {
        super.registerProcessor(processor);
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

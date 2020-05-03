package cn.ideabuffer.process.core.nodes.condition;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.processors.DefaultIfProcessor;
import cn.ideabuffer.process.core.processors.IfProcessor;
import cn.ideabuffer.process.core.rule.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public class IfConditionNode extends AbstractExecutableNode<ProcessStatus, IfProcessor> {

    public IfConditionNode(@NotNull Rule rule, @NotNull BranchNode trueBranch) {
        this(rule, trueBranch, null);
    }

    public IfConditionNode(@NotNull Rule rule, @NotNull BranchNode trueBranch, BranchNode falseBranch) {
        super.registerProcessor(new DefaultIfProcessor(rule, trueBranch, falseBranch));
    }

    @Override
    protected boolean ruleCheck(@NotNull Context context) {
        return true;
    }

    @Override
    protected void onDestroy() {
        try {
            if (getProcessor().getTrueBranch() != null) {
                getProcessor().getTrueBranch().destroy();
            }
            if (getProcessor().getFalseBranch() != null) {
                getProcessor().getFalseBranch().destroy();
            }
        } catch (Exception e) {
            logger.error("destroy encountered problem!", e);
            throw e;
        }

    }
}

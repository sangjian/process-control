package cn.ideabuffer.process.core.nodes.branch;

import cn.ideabuffer.process.core.Lifecycle;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.executor.NodeExecutors;
import cn.ideabuffer.process.core.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.processors.BranchProcessor;
import cn.ideabuffer.process.core.processors.DefaultBranchProcessor;
import cn.ideabuffer.process.core.rule.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/03/01
 */
public class DefaultBranchNode extends AbstractExecutableNode<Void, BranchProcessor> implements BranchNode {

    public DefaultBranchNode() {

    }

    public DefaultBranchNode(ExecutableNode<?, ?>... nodes) {
        this(null, nodes);
    }

    public DefaultBranchNode(List<ExecutableNode<?, ?>> nodes) {
        this(null, nodes);
    }

    public DefaultBranchNode(Rule rule, List<ExecutableNode<?, ?>> nodes) {
        super(rule);
        super.registerProcessor(new DefaultBranchProcessor(nodes));
    }

    public DefaultBranchNode(Rule rule, ExecutableNode<?, ?>... nodes) {
        super(rule);
        super.registerProcessor(new DefaultBranchProcessor(nodes));
    }


    @Override
    protected void onDestroy() {
        try {
            List<ExecutableNode<?, ?>> nodes = getProcessor().getNodes();
            if (nodes != null) {
                nodes.forEach(Lifecycle::destroy);
            }
        } catch (Exception e) {
            logger.error("destroy encountered problem!", e);
            throw e;
        }
    }
}

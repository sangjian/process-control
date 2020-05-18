package cn.ideabuffer.process.core.nodes.branch;

import cn.ideabuffer.process.core.Lifecycle;
import cn.ideabuffer.process.core.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.processors.BranchProcessor;
import cn.ideabuffer.process.core.processors.impl.BranchProcessorImpl;
import cn.ideabuffer.process.core.rule.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/03/01
 */
public class DefaultBranchNode extends AbstractExecutableNode<ProcessStatus, BranchProcessor> implements BranchNode {

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
        super.registerProcessor(new BranchProcessorImpl(nodes));
    }

    public DefaultBranchNode(Rule rule, ExecutableNode<?, ?>... nodes) {
        super(rule);
        super.registerProcessor(new BranchProcessorImpl(nodes));
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

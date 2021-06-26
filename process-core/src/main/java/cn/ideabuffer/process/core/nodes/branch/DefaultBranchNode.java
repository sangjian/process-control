package cn.ideabuffer.process.core.nodes.branch;

import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.nodes.Nodes;
import cn.ideabuffer.process.core.processors.BranchProcessor;
import cn.ideabuffer.process.core.processors.impl.BranchProcessorImpl;
import cn.ideabuffer.process.core.rule.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public DefaultBranchNode(Processor<?>... processors) {
        List<ExecutableNode<?, ?>> nodes = null;
        if (processors != null) {
            nodes = Arrays.stream(processors).map(Nodes::newProcessNode).collect(Collectors.toList());
        }
        super.registerProcessor(new BranchProcessorImpl(nodes));
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
}

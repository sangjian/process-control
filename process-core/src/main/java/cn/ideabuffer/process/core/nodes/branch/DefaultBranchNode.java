package cn.ideabuffer.process.core.nodes.branch;

import cn.ideabuffer.process.core.ComplexNodes;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.nodes.Nodes;
import cn.ideabuffer.process.core.processors.BranchProcessor;
import cn.ideabuffer.process.core.processors.impl.BranchProcessorImpl;
import cn.ideabuffer.process.core.rules.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sangjian.sj
 * @date 2020/03/01
 */
public class DefaultBranchNode extends AbstractExecutableNode<ProcessStatus, BranchProcessor> implements BranchNode {

    public DefaultBranchNode() {
        this((List<ExecutableNode<?, ?>>) null);
    }

    public DefaultBranchNode(ExecutableNode<?, ?>... nodes) {
        this(null, nodes);
    }

    public DefaultBranchNode(Processor<?>... processors) {
        List<ExecutableNode<?, ?>> nodes = null;
        if (processors != null) {
            nodes = Arrays.stream(processors).map(Nodes::newProcessNode).collect(Collectors.toList());
        }
        registerProcessor(new BranchProcessorImpl(nodes));
    }

    public DefaultBranchNode(List<ExecutableNode<?, ?>> nodes) {
        this(null, nodes);
    }

    public DefaultBranchNode(Rule rule, List<ExecutableNode<?, ?>> nodes) {
        super(rule);
        registerProcessor(new BranchProcessorImpl(nodes));
    }

    public DefaultBranchNode(Rule rule, ExecutableNode<?, ?>... nodes) {
        super(rule);
        registerProcessor(new BranchProcessorImpl(nodes));
    }

    @Override
    public List<ExecutableNode<?, ?>> getNodes() {
        List<ExecutableNode<?, ?>> nodes = new LinkedList<>();
        List<ExecutableNode<?, ?>> currentNodes = getProcessor().getNodes();
        if (currentNodes == null) {
            return nodes;
        }
        for (ExecutableNode<?, ?> node : currentNodes) {
            nodes.add(node);
            if (node instanceof ComplexNodes) {
                List<ExecutableNode<?, ?>> cascadeNodes = ((ComplexNodes<ExecutableNode<?, ?>>) node).getNodes();
                if (cascadeNodes != null) {
                    nodes.addAll(cascadeNodes);
                }
            }
        }
        return nodes;
    }
}

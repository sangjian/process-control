package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.Branch;
import cn.ideabuffer.process.core.ComplexNodes;
import cn.ideabuffer.process.core.ProcessListener;
import cn.ideabuffer.process.core.ReturnCondition;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.processors.BranchProcessor;
import cn.ideabuffer.process.core.processors.NodeGroupProcessor;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/03/01
 */
public class NodeGroup<R> extends AbstractExecutableNode<R, NodeGroupProcessor<R>> implements Branch,
    ComplexNodes<ExecutableNode<?, ?>> {

    public NodeGroup() {
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

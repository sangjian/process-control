package cn.ideabuffer.process.core.processors;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.executor.NodeExecutors;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/05/02
 */
public class DefaultBranchProcessor implements BranchProcessor {

    private List<ExecutableNode<?, ?>> nodes;

    public DefaultBranchProcessor(List<ExecutableNode<?, ?>> nodes) {
        this.nodes = nodes;
    }

    public DefaultBranchProcessor(ExecutableNode<?, ?>... nodes) {
        this.nodes = new ArrayList<>();
        if (nodes != null && nodes.length > 0) {
            this.nodes.addAll(Arrays.asList(nodes));
        }
    }

    @Override
    public Void process(@NotNull Context context) throws Exception {
        if (nodes == null) {
            return null;
        }
        NodeExecutors.SERIAL_EXECUTOR.execute(context, this.nodes.toArray(new ExecutableNode[0]));
        return null;
    }

    @Override
    public List<ExecutableNode<?, ?>> getNodes() {
        return nodes;
    }

    public void setNodes(List<ExecutableNode<?, ?>> nodes) {
        this.nodes = nodes;
    }

    @Override
    public void addNodes(ExecutableNode<?, ?>... nodes) {
        if(nodes == null || nodes.length == 0) {
            return;
        }
        this.nodes.addAll(Arrays.asList(nodes));
    }
}

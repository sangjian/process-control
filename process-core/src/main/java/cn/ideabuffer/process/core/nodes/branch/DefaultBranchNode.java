package cn.ideabuffer.process.core.nodes.branch;

import cn.ideabuffer.process.core.Lifecycle;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.executor.NodeExecutors;
import cn.ideabuffer.process.core.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
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
public class DefaultBranchNode extends AbstractExecutableNode<Void> implements BranchNode {

    private List<ExecutableNode<?>> nodes;

    public DefaultBranchNode() {
        this.nodes = new ArrayList<>();
    }

    public DefaultBranchNode(ExecutableNode<?>... nodes) {
        this(null, nodes);
    }

    public DefaultBranchNode(List<ExecutableNode<?>> nodes) {
        this.nodes = new ArrayList<>();
        if (nodes != null) {
            this.nodes.addAll(nodes);
        }
    }

    public DefaultBranchNode(Rule rule, List<ExecutableNode<?>> nodes) {
        super(rule);
        this.nodes = new ArrayList<>();
        if (nodes != null) {
            this.nodes.addAll(nodes);
        }
    }

    public DefaultBranchNode(Rule rule, ExecutableNode<?>... nodes) {
        super(rule);
        this.nodes = new ArrayList<>();
        if (nodes != null && nodes.length > 0) {
            this.nodes.addAll(Arrays.asList(nodes));
        }
    }

    @Override
    public void addNodes(@NotNull ExecutableNode<?>... nodes) {
        if (nodes.length > 0) {
            this.nodes.addAll(Arrays.asList(nodes));
        }
    }

    @Override
    public List<ExecutableNode<?>> getNodes() {
        return nodes;
    }

    public void setNodes(List<ExecutableNode<?>> nodes) {
        this.nodes = nodes;
    }

    @Override
    protected Void doExecute(Context context) throws Exception {
        NodeExecutors.SERIAL_EXECUTOR.execute(context, this.nodes.toArray(new ExecutableNode[0]));
        return null;
    }

    @Override
    public ProcessStatus onComplete(@NotNull Context context, Void result) {
        return ProcessStatus.PROCEED;
    }

    @Override
    public ProcessStatus onFailure(@NotNull Context context, Throwable t) {
        return ProcessStatus.COMPLETE;
    }

    @Override
    protected void onDestroy() {
        try {
            if (nodes != null) {
                nodes.forEach(Lifecycle::destroy);
            }
        } catch (Exception e) {
            logger.error("destroy encountered problem!", e);
            throw e;
        }
    }
}

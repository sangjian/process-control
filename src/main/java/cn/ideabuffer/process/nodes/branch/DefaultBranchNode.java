package cn.ideabuffer.process.nodes.branch;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.handler.ExceptionHandler;
import cn.ideabuffer.process.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.nodes.ExecutableNode;
import cn.ideabuffer.process.rule.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;

import static cn.ideabuffer.process.executor.NodeExecutors.SERIAL_EXECUTOR;

/**
 * @author sangjian.sj
 * @date 2020/03/01
 */
public class DefaultBranchNode extends AbstractExecutableNode implements BranchNode {

    private List<ExecutableNode> nodes;

    public DefaultBranchNode() {
        this.nodes = new ArrayList<>();
    }

    public DefaultBranchNode(ExecutableNode... nodes) {
        this(null, nodes);
    }

    public DefaultBranchNode(List<ExecutableNode> nodes) {
        this.nodes = new ArrayList<>();
        if (nodes != null) {
            this.nodes.addAll(nodes);
        }
    }

    public DefaultBranchNode(Rule rule, List<ExecutableNode> nodes) {
        super(rule);
        this.nodes = new ArrayList<>();
        if (nodes != null) {
            this.nodes.addAll(nodes);
        }
    }

    public DefaultBranchNode(Rule rule, ExecutableNode... nodes) {
        super(rule);
        this.nodes = new ArrayList<>();
        if (nodes != null && nodes.length > 0) {
            this.nodes.addAll(Arrays.asList(nodes));
        }
    }

    @Override
    public DefaultBranchNode addNodes(@NotNull ExecutableNode... nodes) {
        if (nodes.length > 0) {
            this.nodes.addAll(Arrays.asList(nodes));
        }
        return this;
    }

    @Override
    public List<ExecutableNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<ExecutableNode> nodes) {
        this.nodes = nodes;
    }

    @Override
    public BranchNode processOn(Rule rule) {
        super.processOn(rule);
        return this;
    }

    @Override
    public BranchNode parallel() {
        super.parallel();
        return this;
    }

    @Override
    public BranchNode parallel(Executor executor) {
        super.parallel(executor);
        return this;
    }

    @Override
    public BranchNode exceptionHandler(ExceptionHandler handler) {
        super.exceptionHandler(handler);
        return this;
    }

    @Override
    protected boolean doExecute(Context context) throws Exception {
        return SERIAL_EXECUTOR.execute(context, this.nodes.toArray(new ExecutableNode[0]));
    }
}

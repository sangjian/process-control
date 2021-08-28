package cn.ideabuffer.process.core.processors.impl;

import cn.ideabuffer.process.core.LifecycleManager;
import cn.ideabuffer.process.core.ResultHandler;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.executors.NodeExecutors;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.processors.BranchProcessor;
import cn.ideabuffer.process.core.processors.NodeGroupProcessor;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/05/02
 */
public class NodeGroupProcessorImpl<R> implements NodeGroupProcessor<R> {

    private List<ExecutableNode<?, ?>> nodes;
    private ResultHandler<R> resultHandler;

    public NodeGroupProcessorImpl(List<ExecutableNode<?, ?>> nodes, ResultHandler<R> resultHandler) {
        this.nodes = nodes;
        this.resultHandler = resultHandler;
    }

    @NotNull
    @Override
    public R process(@NotNull Context context) throws Exception {
        if (nodes == null) {
            return resultHandler == null ? null : resultHandler.getResult(context);
        }
        NodeExecutors.SERIAL_EXECUTOR.execute(context, this.nodes.toArray(new ExecutableNode[0]));
        return resultHandler == null ? null : resultHandler.getResult(context);
    }

    @Override
    public List<ExecutableNode<?, ?>> getNodes() {
        return nodes;
    }

    @Override
    public ResultHandler<R> getResultHandler() {
        return resultHandler;
    }

    public void setNodes(List<ExecutableNode<?, ?>> nodes) {
        this.nodes = nodes;
    }

    @Override
    public void addNodes(ExecutableNode<?, ?>... nodes) {
        if (nodes == null || nodes.length == 0) {
            return;
        }
        this.nodes.addAll(Arrays.asList(nodes));
    }

    @Override
    public void initialize() {
        if (nodes == null || nodes.isEmpty()) {
            return;
        }
        LifecycleManager.initialize(nodes);
    }

    @Override
    public void destroy() {
        if (nodes == null || nodes.isEmpty()) {
            return;
        }
        LifecycleManager.destroy(nodes);
    }
}

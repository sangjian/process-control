package cn.ideabuffer.process.core.processors.impl;

import cn.ideabuffer.process.core.*;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.processors.ProcessInstanceProcessor;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

/**
 * @author sangjian.sj
 * @date 2020/05/03
 */
public class ProcessInstanceProcessorImpl<R> implements ProcessInstanceProcessor<R> {

    private ProcessDefinition<R> definition;
    @Nullable
    private R result = null;

    public ProcessInstanceProcessorImpl(ProcessDefinition<R> definition) {
        this.definition = definition;
    }

    @NotNull
    @Override
    public ProcessStatus process(@NotNull Context context) throws Exception {

        Node[] nodes = definition.getNodes();
        ProcessStatus status = ProcessStatus.proceed();
        for (int i = 0; i < nodes.length; i++) {
            Node node = nodes[i];
            if (!node.enabled()) {
                continue;
            }

            if (node instanceof ExecutableNode) {
                checkMode(node);
                Context ctx = context;
                if (node instanceof ProcessInstance) {
                    ctx = context.cloneContext();
                }
                status = ((ExecutableNode)node).execute(ctx);
                if (ProcessStatus.isComplete(status)) {
                    break;
                }
            }

        }
        if (definition.getResultKey() != null) {
            result = context.get(definition.getResultKey());
        }

        return status;
    }

    @Nullable
    @Override
    public R getResult() {
        return result;
    }

    @Override
    public ProcessDefinition<R> getProcessDefinition() {
        return definition;
    }

    private void checkMode(Node node) {
        InitializeMode mode = this.definition.getInitializeMode();
        if (mode == InitializeMode.LAZY) {
            LifecycleManager.initialize(node);
        }
    }

    @Override
    public void initialize() {

    }

    @Override
    public void destroy() {

    }
}

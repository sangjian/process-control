package cn.ideabuffer.process.core.processors.impl;

import cn.ideabuffer.process.core.*;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.exception.ProcessException;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.processors.ProcessInstanceProcessor;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/05/03
 */
public class ProcessInstanceProcessorImpl<R> implements ProcessInstanceProcessor<R> {

    private ProcessDefinition<R> definition;
    private R result = null;

    public ProcessInstanceProcessorImpl(ProcessDefinition<R> definition) {
        this.definition = definition;
    }

    @NotNull
    @Override
    public ProcessStatus process(@NotNull Context context) throws Exception {
        checkState();

        Node[] nodes = definition.getNodes();
        ProcessStatus status = ProcessStatus.proceed();
        context.setResultKey(definition);
        for (int i = 0; i < nodes.length; i++) {
            Node node = nodes[i];
            if (!node.enabled()) {
                continue;
            }

            if (node instanceof ExecutableNode) {
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
        if (context.getResultKey() != null) {
            result = context.get(context.getResultKey());
        }

        return status;
    }

    @Override
    public R getResult() {
        return result;
    }

    @Override
    public ProcessDefinition<R> getProcessDefinition() {
        return definition;
    }

    private void checkState() {
        LifecycleState state = this.definition.getState();
        InitializeMode mode = this.definition.getInitializeMode();
        if (mode == InitializeMode.LAZY) {
            try {
                this.definition.initialize();
            } catch (Exception e) {
                throw new ProcessException("initialize failed", e);
            }

        }
        if (!state.isAvailable()) {
            throw new ProcessException(String.format("current state [%s] is not available", state));
        }
    }
}

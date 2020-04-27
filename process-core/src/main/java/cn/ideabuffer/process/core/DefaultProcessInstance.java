package cn.ideabuffer.process.core;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.exception.ProcessException;
import cn.ideabuffer.process.core.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.core.nodes.BaseNode;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public class DefaultProcessInstance<R> extends AbstractExecutableNode implements ProcessInstance<R> {

    private ProcessDefinition<R> definition;

    private R result = null;

    public DefaultProcessInstance(@NotNull ProcessDefinition<R> definition) {
        this.definition = definition;
    }

    @NotNull
    @Override
    public ProcessStatus doExecute(Context context) throws Exception {
        checkState();
        Context current = context == null ? Contexts.newContext() : context;

        Exception exception = null;

        Node[] nodes = definition.getNodes();
        ProcessStatus status = ProcessStatus.PROCEED;
        int i = 0;
        for (; i < nodes.length; i++) {
            Node node = nodes[i];

            if (!node.enabled()) {
                continue;
            }

            if (node instanceof ExecutableNode) {
                try {
                    Context ctx = current;
                    if (node instanceof ProcessInstance) {
                        ctx = current.cloneContext();
                    }
                    status = ((ExecutableNode)node).execute(ctx);
                    if (ProcessStatus.isComplete(status)) {
                        break;
                    }
                } catch (Exception e) {
                    exception = e;
                    break;
                }
            }

        }
        if (i >= nodes.length) {
            i--;
        }
        BaseNode<R> baseNode = definition.getBaseNode();
        if (baseNode != null && baseNode.enabled()) {
            try {
                result = definition.getBaseNode().invoke(current, status);
            } catch (Exception e) {
                exception = e;
            }
        }

        List<Node> postNodeList = Arrays.stream(nodes).collect(Collectors.toList()).subList(0, ++i);
        boolean baseProcessed = postProcess(definition.getBaseNode(), context, exception);
        boolean chainProcessed = postProcess(postNodeList, current, exception);

        if (exception == null) {
            return status;
        }
        if (!baseProcessed && !chainProcessed) {
            throw exception;
        }

        return status;
    }

    private boolean postProcess(Node node, Context context, Exception exception) {
        if (node == null) {
            return false;
        }
        try {
            if (!node.enabled()) {
                return false;
            }
            if (node instanceof PostProcessor) {
                return ((PostProcessor)node).postProcess(context, exception);
            }

        } catch (Exception e) {
            // do something...
        }
        return false;
    }

    private boolean postProcess(List<Node> nodes, Context context, Exception exception) {
        boolean processed = false;
        for (int i = nodes.size() - 1; i >= 0; i--) {
            if (postProcess(nodes.get(i), context, exception)) {
                processed = true;
            }
        }
        return processed;
    }

    @Override
    public boolean enabled() {
        return true;
    }

    @Override
    public ProcessDefinition<R> getProcessDefinition() {
        return definition;
    }

    @Override
    public R getResult() {
        return result;
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
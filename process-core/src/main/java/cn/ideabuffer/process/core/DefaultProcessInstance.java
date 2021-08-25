package cn.ideabuffer.process.core;

import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.processors.ProcessInstanceProcessor;
import cn.ideabuffer.process.core.processors.impl.ProcessInstanceProcessorImpl;
import cn.ideabuffer.process.core.processors.wrapper.proxy.ProcessInstanceProcessorProxy;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public class DefaultProcessInstance<R> extends AbstractExecutableNode<ProcessStatus, ProcessInstanceProcessor<R>>
    implements ProcessInstance<R> {

    private ProcessDefinition<R> definition;

    public DefaultProcessInstance(@NotNull ProcessDefinition<R> definition) {
        this.definition = definition;
        ProcessInstanceProcessor<R> wrapped = new ProcessInstanceProcessorImpl<>(definition);
        wrapped = ProcessInstanceProcessorProxy.wrap(wrapped, definition.getHandlers());
        super.registerProcessor(wrapped);
        super.setReadableKeys(definition.getDeclaredKeys());
    }

    @Nullable
    @Override
    public R getResult() {
        return getProcessor().getResult();
    }

    @Override
    public boolean enabled() {
        return true;
    }

    private void checkDeclaringKeys(ProcessDefinition<R> definition) {

    }

    @Override
    public List<Node> getNodes() {
        List<Node> nodes = new LinkedList<>();
        if (definition.getNodes() == null) {
            return nodes;
        }
        for (Node node : definition.getNodes()) {
            nodes.add(node);
            if (node instanceof ComplexNodes) {
                List<Node> cascadeNodes = ((ComplexNodes<Node>) node).getNodes();
                if (cascadeNodes != null) {
                    nodes.addAll(cascadeNodes);
                }
            }
        }
        return nodes;
    }
}
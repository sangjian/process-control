package cn.ideabuffer.process.core;

import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.processors.ProcessInstanceProcessor;
import cn.ideabuffer.process.core.processors.impl.ProcessInstanceProcessorImpl;
import cn.ideabuffer.process.core.processors.wrapper.WrapperHandler;
import cn.ideabuffer.process.core.processors.wrapper.proxy.ProcessInstanceProcessorProxy;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public class DefaultProcessInstance<R> extends AbstractExecutableNode<ProcessStatus, ProcessInstanceProcessor<R>>
    implements ProcessInstance<R> {

    public DefaultProcessInstance(@NotNull ProcessDefinition<R> definition) {
        this(definition, null);
    }

    public DefaultProcessInstance(@NotNull ProcessDefinition<R> definition, List<WrapperHandler<ProcessStatus>> handlers) {
        ProcessInstanceProcessor<R> wrapped = new ProcessInstanceProcessorImpl<>(definition);
        wrapped = ProcessInstanceProcessorProxy.wrap(wrapped, handlers);
        super.registerProcessor(wrapped);
        super.setReadableKeys(getAllResultKeys(definition));
    }

    private Set<Key<?>> getAllResultKeys(@NotNull ProcessDefinition<R> definition) {
        Node[] nodes = definition.getNodes();
        Set<Key<?>> keys = new HashSet<>();
        Arrays.stream(nodes).filter(node -> node instanceof ExecutableNode).forEach(node -> {
            if (((ExecutableNode)node).getResultKey() != null) {
                keys.add(((ExecutableNode)node).getResultKey());
            }
        });
        return keys;
    }

    @Override
    public R getResult() {
        return getProcessor().getResult();
    }

    @Override
    public boolean enabled() {
        return true;
    }

}
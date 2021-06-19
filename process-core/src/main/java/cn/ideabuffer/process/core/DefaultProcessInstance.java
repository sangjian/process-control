package cn.ideabuffer.process.core;

import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.core.processors.ProcessInstanceProcessor;
import cn.ideabuffer.process.core.processors.impl.ProcessInstanceProcessorImpl;
import cn.ideabuffer.process.core.processors.wrapper.StatusWrapperHandler;
import cn.ideabuffer.process.core.processors.wrapper.proxy.ProcessInstanceProcessorProxy;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public class DefaultProcessInstance<R> extends AbstractExecutableNode<ProcessStatus, ProcessInstanceProcessor<R>>
    implements ProcessInstance<R> {

    public DefaultProcessInstance(@NotNull ProcessDefinition<R> definition) {
        this(definition, null);
    }

    public DefaultProcessInstance(@NotNull ProcessDefinition<R> definition, List<StatusWrapperHandler> handlers) {
        ProcessInstanceProcessor<R> wrapped = new ProcessInstanceProcessorImpl<>(definition);
        wrapped = ProcessInstanceProcessorProxy.wrap(wrapped, handlers);
        super.registerProcessor(wrapped);
        if (definition.getResultKey() != null) {
            Set<Key<?>> resultKeys = new HashSet<>();
            resultKeys.add(definition.getResultKey());
            super.setReadableKeys(resultKeys);
        }

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
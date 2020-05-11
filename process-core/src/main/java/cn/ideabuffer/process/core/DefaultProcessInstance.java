package cn.ideabuffer.process.core;

import cn.ideabuffer.process.core.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.core.processors.impl.ProcessInstanceProcessorImpl;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public class DefaultProcessInstance<R> extends AbstractExecutableNode<ProcessStatus, ProcessInstanceProcessorImpl<R>> implements ProcessInstance<R> {


    public DefaultProcessInstance(@NotNull ProcessDefinition<R> definition) {
        super.registerProcessor(new ProcessInstanceProcessorImpl<>(definition));
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
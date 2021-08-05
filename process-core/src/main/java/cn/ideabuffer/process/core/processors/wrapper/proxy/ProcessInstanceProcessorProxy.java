package cn.ideabuffer.process.core.processors.wrapper.proxy;

import cn.ideabuffer.process.core.ProcessDefinition;
import cn.ideabuffer.process.core.processors.ProcessInstanceProcessor;
import cn.ideabuffer.process.core.processors.wrapper.StatusWrapperHandler;
import cn.ideabuffer.process.core.processors.wrapper.WrapperHandler;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2021/06/17
 */
public class ProcessInstanceProcessorProxy<R> extends AbstractProcessorProxy<ProcessInstanceProcessor<R>, ProcessStatus>
    implements ProcessInstanceProcessor<R> {

    public ProcessInstanceProcessorProxy(
        @NotNull ProcessInstanceProcessor<R> target,
        @NotNull WrapperHandler<ProcessStatus> handler) {
        super(target, handler);
    }

    public static <R> ProcessInstanceProcessor<R> wrap(@NotNull ProcessInstanceProcessor<R> target,
        List<StatusWrapperHandler> handlers) {
        if (handlers == null || handlers.isEmpty()) {
            return target;
        }
        ProcessInstanceProcessor<R> wrapped = target;
        for (int i = handlers.size() - 1; i >= 0; i--) {
            wrapped = new ProcessInstanceProcessorProxy<>(wrapped, handlers.get(i));
        }
        return wrapped;
    }

    @Override
    public ProcessDefinition<R> getProcessDefinition() {return getTarget().getProcessDefinition();}

    @Override
    public R getResult() {return getTarget().getResult();}
}

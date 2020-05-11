package cn.ideabuffer.process.core.processors;

import cn.ideabuffer.process.core.ProcessDefinition;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/05/03
 */
public interface ProcessInstanceProcessor<R> extends Processor<ProcessStatus> {

    /**
     * 获取流程定义
     *
     * @return 流程定义
     */
    ProcessDefinition<R> getProcessDefinition();

    /**
     * 获取返回结果
     *
     * @return 结果
     */
    R getResult();

    @NotNull
    @Override
    ProcessStatus process(@NotNull Context context) throws Exception;
}

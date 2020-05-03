package cn.ideabuffer.process.core.processors;

import cn.ideabuffer.process.core.*;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.exception.ProcessException;
import cn.ideabuffer.process.core.nodes.BaseNode;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

}

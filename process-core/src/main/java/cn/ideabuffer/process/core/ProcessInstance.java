package cn.ideabuffer.process.core;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.processors.ProcessInstanceProcessor;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 流程实例接口，一个流程实例表示一次具体的业务处理流程。
 *
 * @author sangjian.sj
 * @date 2020/01/18
 */
public interface ProcessInstance<R> extends ExecutableNode<ProcessStatus, ProcessInstanceProcessor<R>>, ComplexNodes<Node> {

    @Nullable
    R process(@NotNull Context context) throws Exception;

    @Nullable
    R process(@NotNull ContextBuilder builder) throws Exception;

    @Nullable
    R getResult();

}

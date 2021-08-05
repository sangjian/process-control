package cn.ideabuffer.process.core.executors;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

/**
 * 串行执行器
 *
 * @author sangjian.sj
 * @date 2020/02/25
 */
public interface SerialExecutor {

    /**
     * 执行节点
     *
     * @param context 流程上下文
     * @param nodes   可执行节点列表
     * @return 流程状态
     * @throws Exception
     */
    @NotNull
    ProcessStatus execute(@NotNull Context context, ExecutableNode<?, ?>... nodes) throws Exception;
}

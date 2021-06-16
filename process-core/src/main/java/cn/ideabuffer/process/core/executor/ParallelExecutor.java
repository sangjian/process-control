package cn.ideabuffer.process.core.executor;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.status.ProcessStatus;
import cn.ideabuffer.process.core.strategy.ProceedStrategy;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;

/**
 * 并行执行器
 *
 * @author sangjian.sj
 * @date 2020/02/25
 */
public interface ParallelExecutor {

    /**
     * 并行执行节点
     *
     * @param executor        指定执行器来并行执行
     * @param proceedStrategy 并行继续策略
     * @param context         当前流程上下文
     * @param nodes           可执行节点列表
     * @throws Exception
     */
    @NotNull
    ProcessStatus execute(Executor executor, @NotNull ProceedStrategy proceedStrategy, @NotNull Context context,
        ExecutableNode<?, ?>... nodes)
        throws Exception;
}

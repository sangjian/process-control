package cn.ideabuffer.process.executor;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.Executable;
import cn.ideabuffer.process.nodes.ExecutableNode;
import cn.ideabuffer.process.strategy.ProceedStrategy;

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
     * @return <li>false: 继续执行整个实例的下游节点</li><li>true: 不再执行整个实例的下游节点</li>
     * @throws Exception
     * @see Executable#CONTINUE_PROCESSING
     * @see Executable#PROCESSING_COMPLETE
     */
    boolean execute(Executor executor, ProceedStrategy proceedStrategy, Context context, ExecutableNode... nodes)
        throws Exception;
}

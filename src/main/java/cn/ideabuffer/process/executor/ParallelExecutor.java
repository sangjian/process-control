package cn.ideabuffer.process.executor;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.nodes.ExecutableNode;
import cn.ideabuffer.process.strategy.ProceedStrategy;

import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/02/25
 */
public interface ParallelExecutor {

    boolean execute(Executor executor, ProceedStrategy proceedStrategy, Context context, ExecutableNode... nodes)
        throws Exception;
}

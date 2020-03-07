package cn.ideabuffer.process.executor;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.ExecutableNode;

import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/02/25
 */
public interface SerialExecutor {

    boolean execute(Context context, ExecutableNode... nodes) throws Exception;
}

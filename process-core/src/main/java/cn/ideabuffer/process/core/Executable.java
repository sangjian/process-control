package cn.ideabuffer.process.core;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;

/**
 * 提供可执行的能力
 *
 * @author sangjian.sj
 * @date 2020/01/19
 */
public interface Executable {

    /**
     * 根据上下文执行当前节点
     *
     * @param context 流程上下文
     * @return 状态
     * @throws Exception
     * @see ProcessStatus
     */
    @NotNull
    ProcessStatus execute(Context context) throws Exception;

    /**
     * 获取当前执行的线程池
     *
     * @return
     */
    Executor getExecutor();

}

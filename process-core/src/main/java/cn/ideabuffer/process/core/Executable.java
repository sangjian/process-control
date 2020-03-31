package cn.ideabuffer.process.core;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.handler.ExceptionHandler;
import cn.ideabuffer.process.core.status.ProcessStatus;

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
    ProcessStatus execute(Context context) throws Exception;

    /**
     * 设置异常处理器
     *
     * @param handler 异常处理器
     * @return 当前对象
     * @see ExceptionHandler
     */
    Executable exceptionHandler(ExceptionHandler handler);

    /**
     * 获取异常处理器
     *
     * @return 异常处理器对象
     */
    ExceptionHandler getExceptionHandler();

    /**
     * 获取当前执行的线程池
     *
     * @return
     */
    Executor getExecutor();

}

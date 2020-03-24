package cn.ideabuffer.process;

import cn.ideabuffer.process.handler.ExceptionHandler;
import cn.ideabuffer.process.status.ProcessStatus;

import java.util.concurrent.Executor;

/**
 * 提供可执行的能力
 *
 * @author sangjian.sj
 * @date 2020/01/19
 */
public interface Executable {

    /**
     * 表示流程为完成，将继续执行整个实例的下游节点
     */
    boolean CONTINUE_PROCESSING = false;

    /**
     * 表示流程已完成，不再执行整个实例的下游节点
     */
    boolean PROCESSING_COMPLETE = true;

    /**
     * 根据上下文执行当前节点
     *
     * @param context 流程上下文
     * @return <li>false: 继续执行整个实例的下游节点</li><li>true: 不再执行整个实例的下游节点</li>
     * @throws Exception
     * @see Executable#CONTINUE_PROCESSING
     * @see Executable#PROCESSING_COMPLETE
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

package cn.ideabuffer.process;

import cn.ideabuffer.process.handler.ExceptionHandler;

import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/01/19
 */
public interface Executable {

    /**
     * 根据上下文执行当前节点
     *
     * @param context 流程上下文
     * @return 返回值为true，表示执行完成，终止流程，不再向下执行；返回值为false，继续向下执行
     * @throws Exception
     */
    boolean execute(Context context) throws Exception;

    Executable exceptionHandler(ExceptionHandler handler);

    ExceptionHandler getExceptionHandler();

    /**
     * 获取当前执行的线程池
     *
     * @return
     */
    Executor getExecutor();

}

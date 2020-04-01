package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.Node;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.handler.ExceptionHandler;
import cn.ideabuffer.process.core.status.ProcessStatus;

/**
 * 基础节点，在整个流程最后执行，用于计算结果并返回
 *
 * @author sangjian.sj
 * @date 2020/03/24
 */
public interface BaseNode<R> extends Node {

    /**
     * 执行并返回结果
     *
     * @param context
     * @return
     */
    R invoke(Context context, ProcessStatus status);

    /**
     * 设置异常处理器
     *
     * @param handler 异常处理器
     * @return 当前对象
     * @see ExceptionHandler
     */
    BaseNode<R> exceptionHandler(ExceptionHandler handler);

    /**
     * 获取异常处理器
     *
     * @return 异常处理器对象
     */
    ExceptionHandler getExceptionHandler();

}

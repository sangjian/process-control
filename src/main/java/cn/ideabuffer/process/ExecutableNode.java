package cn.ideabuffer.process;

import cn.ideabuffer.process.handler.ExceptionHandler;
import cn.ideabuffer.process.rule.Rule;

import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/01/19
 */
public interface ExecutableNode extends Node {

    boolean CONTINUE_PROCESSING = false;

    boolean PROCESSING_COMPLETE = true;

    /**
     * 根据上下文执行当前节点
     *
     * @param context 流程上下文
     * @return 返回值为true，表示执行完成，终止流程，不再向下执行；返回值为false，继续向下执行
     * @throws Exception
     */
    boolean execute(Context context) throws Exception;

    ExecutableNode exceptionHandler(ExceptionHandler handler);

    ExceptionHandler getExceptionHandler();

    /**
     * 获取当前执行的线程池
     *
     * @return
     */
    Executor getExecutor();

    ExecutableNode parallel();

    ExecutableNode parallel(Executor executor);

    ExecutableNode processOn(Rule rule);

    Rule getRule();

}

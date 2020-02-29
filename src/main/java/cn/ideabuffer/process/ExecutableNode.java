package cn.ideabuffer.process;

import java.util.concurrent.ExecutorService;

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

    /**
     * 获取当前执行的线程池
     *
     * @return
     */
    ExecutorService getExecutor();

    /**
     * 执行线程池执行
     *
     * @param executor
     * @return
     */
    ExecutableNode executeOn(ExecutorService executor);

}

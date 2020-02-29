package cn.ideabuffer.process.executor;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.ExecutableNode;

import java.util.concurrent.ExecutorService;

/**
 * @author sangjian.sj
 * @date 2020/02/24
 */
public interface ExecuteStrategy {

    /**
     * 在执行的线程池中执行节点
     *
     * @param executor 执行线程池执行
     * @param context 流程上下文
     * @param nodes 可执行节点列表
     * @return
     * @throws Exception
     */
    boolean execute(ExecutorService executor, Context context, ExecutableNode[] nodes) throws Exception;
}

package cn.ideabuffer.process.executor;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.ExecutableNode;

import java.util.concurrent.ExecutorService;

/**
 * @author sangjian.sj
 * @date 2020/02/24
 */
public interface ExecuteStrategy {

    boolean execute(ExecutorService executor, Context context, ExecutableNode[] nodes) throws Exception;
}

package cn.ideabuffer.process;

import java.util.concurrent.ExecutorService;

/**
 * @author sangjian.sj
 * @date 2020/01/19
 */
public interface ExecutableNode extends Node {

    boolean execute(Context context) throws Exception;

    ExecutorService getExecutor();

    ExecutableNode executeOn(ExecutorService executor);

}

package cn.ideabuffer.process.core.executor;

import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/03/07
 */
public class NodeExecutors {

    /**
     * 默认串行执行器
     */
    public static final SerialExecutor SERIAL_EXECUTOR = new DefaultSerialExecutor();
    /**
     * 默认并行执行器
     */
    public static final ParallelExecutor PARALLEL_EXECUTOR = new DefaultParallelExecutor();
    /**
     * 默认线程池，该线程每执行时会创建一个新线程
     */
    public static final Executor DEFAULT_POOL = new ThreadPerTaskExecutor();

    private NodeExecutors() {
        throw new IllegalStateException("Utility class");
    }

    static final class ThreadPerTaskExecutor implements Executor {

        @Override
        public void execute(Runnable r) {
            new Thread(r).start();
        }
    }

}

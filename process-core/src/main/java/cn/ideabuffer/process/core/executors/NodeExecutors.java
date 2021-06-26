package cn.ideabuffer.process.core.executors;

import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

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
     * 默认线程池，使用默认commonPool
     */
    public static final Executor DEFAULT_POOL = ForkJoinPool.commonPool();

    private NodeExecutors() {
        throw new IllegalStateException("Utility class");
    }

}

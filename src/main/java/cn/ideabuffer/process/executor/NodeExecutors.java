package cn.ideabuffer.process.executor;

import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/03/07
 */
public class NodeExecutors {

    public static final SerialExecutor SERIAL_EXECUTOR = new DefaultSerialExecutor();

    public static final ParallelExecutor PARALLEL_EXECUTOR = new DefaultParallelExecutor();

    public static final Executor DEFAULT_POOL = new ThreadPerTaskExecutor();

    static final class ThreadPerTaskExecutor implements Executor {

        @Override
        public void execute(Runnable r) {
            new Thread(r).start();
        }
    }

}

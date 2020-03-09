package cn.ideabuffer.process.executor;

/**
 * @author sangjian.sj
 * @date 2020/03/07
 */
public class NodeExecutors {

    public static final SerialExecutor SERIAL_EXECUTOR = new DefaultSerialExecutor();

    public static final ParallelExecutor PARALLEL_EXECUTOR = new DefaultParallelExecutor();

}

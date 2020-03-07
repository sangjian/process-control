package cn.ideabuffer.process.executor;

/**
 * @author sangjian.sj
 * @date 2020/03/07
 */
public class NodeExecutors {

    public static final NodeExecutor SERIAL_EXECUTOR = new DefaultSerialExecutor();

    public static final NodeExecutor PARALLEL_EXECUTOR = new DefaultParallelExecutor();

}

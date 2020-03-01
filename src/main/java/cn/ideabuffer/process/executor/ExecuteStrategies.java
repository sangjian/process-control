package cn.ideabuffer.process.executor;

/**
 * @author sangjian.sj
 * @date 2020/02/25
 */
public class ExecuteStrategies {

    public static final ExecuteStrategy SERIAL = new SerialStrategy();

    public static final ExecuteStrategy ASYNC = new AsyncStrategy();

    public static final ExecuteStrategy PARALLEL = new ParallelStrategy();

    public static final ExecuteStrategy AT_LEAST_ONE_DONE = new AtLeastOneDoneStrategy();

    public static final ExecuteStrategy AT_LEAST_ONE_CONTINUED = new AtLeastOneContinuedStrategy();

    public static final ExecuteStrategy ALL_DONE = new AllDoneStrategy();

    public static final ExecuteStrategy ALL_CONTINUED = new AllContinuedStrategy();

}

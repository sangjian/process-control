package cn.ideabuffer.process.executor;

/**
 * @author sangjian.sj
 * @date 2020/02/25
 */
public class ExecuteStrategies {

    public static final ExecuteStrategy SERIAL = new SerialExecuteStrategy();

    public static final ExecuteStrategy ASYNC = new AsyncExecuteStrategy();

    public static final ExecuteStrategy AT_LEAST_ONE_COMPLETE = new AtLeastOneCompleteExecuteStrategy();

    public static final ExecuteStrategy ALL_COMPLETE = new AllCompleteExecuteStrategy();

}

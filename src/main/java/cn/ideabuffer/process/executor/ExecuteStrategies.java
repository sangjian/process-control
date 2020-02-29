package cn.ideabuffer.process.executor;

/**
 * @author sangjian.sj
 * @date 2020/02/25
 */
public class ExecuteStrategies {

    public static final ExecuteStrategy SERIAL = new SerialExecuteStrategy();

    public static final ExecuteStrategy ASYNC = new AsyncExecuteStrategy();

    public static final ExecuteStrategy ANY_OF = new AnyOfExecuteStrategy();

    public static final ExecuteStrategy ALL_OF = new AllOfExecuteStrategy();

}

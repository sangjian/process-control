package cn.ideabuffer.process.executor;

/**
 * @author sangjian.sj
 * @date 2020/02/25
 */
public class ExecuteStrategies {

    public static final ExecuteStrategy SERIAL_PROCEEDED = new SerialStrategy();

    public static final ExecuteStrategy PARALLELED = new ParallelStrategy();

    public static final ExecuteStrategy AT_LEAST_ONE_FINISHED = new AtLeastOneFinishedStrategy();

    public static final ExecuteStrategy AT_LEAST_ONE_PROCEEDED = new AtLeastOneProceededStrategy();

    public static final ExecuteStrategy ALL_FINISHED = new AllFinishedStrategy();

    public static final ExecuteStrategy ALL_PROCEEDED = new AllProceededStrategy();

}

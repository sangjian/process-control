package cn.ideabuffer.process.strategy;

/**
 * @author sangjian.sj
 * @date 2020/02/25
 */
public class ProceedStrategies {

    public static final ProceedStrategy AT_LEAST_ONE_PROCEEDED = new AtLeastOneProceededStrategy();

    public static final ProceedStrategy AT_LEAST_ONE_FINISHED = new AtLeastOneFinishedStrategy();

    public static final ProceedStrategy ALL_PROCEEDED = new AllProceededStrategy();

    public static final ProceedStrategy ALL_FINISHED = new AllFinishedStrategy();

}

package cn.ideabuffer.process.nodes.aggregate;

/**
 * @author sangjian.sj
 * @date 2020/03/09
 */
public class Aggregators {

    public static final Aggregator SERIAL = new SerialAggregator();

    public static final Aggregator PARALLEL = new ParallelAggregator();

}

package cn.ideabuffer.process.executor;

import cn.ideabuffer.process.nodes.aggregate.Aggregator;
import cn.ideabuffer.process.nodes.aggregate.DefaultAggregator;

/**
 * @author sangjian.sj
 * @date 2020/03/07
 */
public class NodeExecutors {

    public static final SerialExecutor SERIAL_EXECUTOR = new DefaultSerialExecutor();

    public static final ParallelExecutor PARALLEL_EXECUTOR = new DefaultParallelExecutor();

    public static final Aggregator DEFAULT_AGGREGATOR = new DefaultAggregator();

}

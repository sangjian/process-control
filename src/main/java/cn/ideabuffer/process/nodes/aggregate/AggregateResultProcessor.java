package cn.ideabuffer.process.nodes.aggregate;

import cn.ideabuffer.process.Context;

/**
 * @author sangjian.sj
 * @date 2020/03/08
 */
public interface AggregateResultProcessor<R, T> {

    R apply(Context context, T result);

}

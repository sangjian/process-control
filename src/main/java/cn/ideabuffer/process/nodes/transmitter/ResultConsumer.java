package cn.ideabuffer.process.nodes.transmitter;

import cn.ideabuffer.process.context.Context;

/**
 * @author sangjian.sj
 * @date 2020/03/08
 */
@FunctionalInterface
public interface ResultConsumer<T> {

    void accept(Context context, T result);

}

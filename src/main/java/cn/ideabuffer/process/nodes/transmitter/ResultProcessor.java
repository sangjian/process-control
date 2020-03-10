package cn.ideabuffer.process.nodes.transmitter;

import cn.ideabuffer.process.Context;

/**
 * @author sangjian.sj
 * @date 2020/03/08
 */
@FunctionalInterface
public interface ResultProcessor<R, P> {

    R apply(Context context, P result);

}

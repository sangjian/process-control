package cn.ideabuffer.process.nodes.aggregate;

import cn.ideabuffer.process.Context;

/**
 * @author sangjian.sj
 * @date 2020/03/08
 */
public interface ResultConsumer<T> {

    void accept(Context context, T result);

}

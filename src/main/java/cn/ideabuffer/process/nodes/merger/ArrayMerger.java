package cn.ideabuffer.process.nodes.merger;

import java.util.Collection;

/**
 * @author sangjian.sj
 * @date 2020/03/09
 */
public interface ArrayMerger<T> extends Merger<T[]> {

    @Override
    T[] merge(T[]... results);

    @Override
    T[] merge(Collection<T[]> results);
}

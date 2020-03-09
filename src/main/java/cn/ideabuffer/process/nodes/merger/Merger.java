package cn.ideabuffer.process.nodes.merger;

import java.util.Collection;

/**
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface Merger<T> {

    T merge(T... results);

    T merge(Collection<T> results);

}

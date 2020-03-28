package cn.ideabuffer.process.nodes.merger;

import java.util.Collection;

/**
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface Merger<P, R> {

    R merge(Collection<P> results);

}

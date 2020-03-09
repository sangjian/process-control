package cn.ideabuffer.process.nodes.merger;

import java.util.Collection;
import java.util.Set;

/**
 * @author sangjian.sj
 * @date 2020/03/09
 */
public interface SetMerger<T> extends Merger<Set<T>> {

    @Override
    Set<T> merge(Set<T>... results);

    @Override
    Set<T> merge(Collection<Set<T>> results);
}

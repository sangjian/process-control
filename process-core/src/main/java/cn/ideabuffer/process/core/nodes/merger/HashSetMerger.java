package cn.ideabuffer.process.core.nodes.merger;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author sangjian.sj
 * @date 2020/03/09
 */
public class HashSetMerger<T> implements SetMerger<T> {

    @Override
    public HashSet<T> merge(@NotNull Collection<Set<T>> results) {
        HashSet<T> hashSet = new HashSet<>();
        results.forEach(hashSet::addAll);

        return hashSet;
    }
}

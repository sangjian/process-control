package cn.ideabuffer.process.nodes.merger;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author sangjian.sj
 * @date 2020/03/09
 */
public class HashSetMerger<T> implements SetMerger<T> {

    @Override
    public HashSet<T> merge(Set<T>... results) {
        if (results == null) {
            return new HashSet<>();
        }
        return merge(Arrays.stream(results).collect(Collectors.toList()));
    }

    @Override
    public HashSet<T> merge(Collection<Set<T>> results) {
        if (results == null) {
            return new HashSet<>();
        }
        HashSet<T> hashSet = new HashSet<>();
        results.forEach(hashSet::addAll);

        return hashSet;
    }
}

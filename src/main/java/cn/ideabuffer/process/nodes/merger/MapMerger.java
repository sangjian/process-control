package cn.ideabuffer.process.nodes.merger;

import java.util.*;

/**
 * @author sangjian.sj
 * @date 2020/03/09
 */
public interface MapMerger<K, V> extends Merger<Map<K, V>> {

    @Override
    Map<K, V> merge(Map<K, V>... results);

    @Override
    Map<K, V> merge(Collection<Map<K, V>> results);
}

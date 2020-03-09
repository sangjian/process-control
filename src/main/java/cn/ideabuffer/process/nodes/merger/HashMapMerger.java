package cn.ideabuffer.process.nodes.merger;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author sangjian.sj
 * @date 2020/03/09
 */
public class HashMapMerger<K, V> implements MapMerger<K, V>{

    @Override
    public HashMap<K, V> merge(Map<K, V>... results) {
        if(results == null) {
            return null;
        }
        return merge(Arrays.stream(results).collect(Collectors.toSet()));
    }

    @Override
    public HashMap<K, V> merge(Collection<Map<K, V>> results) {
        if(results == null) {
            return null;
        }
        HashMap<K, V> result = new HashMap<>();
        results.forEach(result::putAll);
        return result;
    }
}

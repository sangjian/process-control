package cn.ideabuffer.process.core.nodes.merger;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;

/**
 * @author sangjian.sj
 * @date 2020/03/09
 */
public interface MapMerger<K, V> extends UnitMerger<Map<K, V>> {

    @Override
    Map<K, V> merge(@NotNull Collection<Map<K, V>> results);
}

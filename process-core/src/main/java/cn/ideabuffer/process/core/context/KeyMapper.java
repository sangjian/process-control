package cn.ideabuffer.process.core.context;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sangjian.sj
 * @date 2020/05/20
 */
public class KeyMapper {

    private Map<Key<?>, Key<?>> mapper;

    public KeyMapper() {
        this.mapper = new ConcurrentHashMap<>();
    }

    public <V> void map(@NotNull Key<V> from, @NotNull Key<V> to) {
        this.mapper.put(from, to);
    }

    public <V> Key<V> getMappingKey(@NotNull Key<V> key) {
        //noinspection unchecked
        return (Key<V>)this.mapper.get(key);
    }

    public boolean isEmpty() {
        return this.mapper.isEmpty();
    }
}

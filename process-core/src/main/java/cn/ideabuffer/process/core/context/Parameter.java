package cn.ideabuffer.process.core.context;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * @author sangjian.sj
 * @date 2020/03/26
 */
public interface Parameter {

    <V> V put(@NotNull Key<V> key, V value);

    <V> V putIfAbsent(@NotNull Key<V> key, V value);

    /**
     * 获取指定类型的值
     *
     * @param key key
     * @param <V> 返回值类型
     * @return
     */
    <V> V get(@NotNull Key<V> key);

    /**
     * 获取指定key的值，如果为null，则取默认值
     *
     * @param key          key
     * @param defaultValue 默认值
     * @param <V>          返回值类型
     * @return
     */
    <V> V get(@NotNull Key<V> key, V defaultValue);

    Map<Key<?>, Object> getParams();

    int size();

    boolean isEmpty();

    <V> boolean containsKey(Key<V> key);

    boolean containsValue(Object value);

    <V> V remove(Key<V> key);

    void putAll(@NotNull Map<? extends Key<?>, ?> params);

    void clear();
}

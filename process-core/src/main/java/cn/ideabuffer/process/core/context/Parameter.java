package cn.ideabuffer.process.core.context;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * 参数接口，定义对参数的操作
 *
 * @author sangjian.sj
 * @date 2020/03/26
 */
public interface Parameter {

    <V> V put(@NotNull Key<V> key, @NotNull V value);

    <V> V putIfAbsent(@NotNull Key<V> key, @NotNull V value);

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

    boolean containsKey(@NotNull Key<?> key);

    boolean containsValue(@NotNull Object value);

    <V> V remove(@NotNull Key<V> key);

    void putAll(@NotNull Map<? extends Key<?>, ?> params);

    void clear();
}

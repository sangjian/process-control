package cn.ideabuffer.process.core.context;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * 参数接口，定义对参数的操作
 *
 * @author sangjian.sj
 * @date 2020/03/26
 */
public interface Parameter {

    /**
     * 添加键值映射
     *
     * @param key   与指定值关联的键
     * @param value 与指定键关联的值
     * @param <V>   值类型
     * @return 上一次当前<tt>key</tt>对应的<tt>value</tt>，如果之前没有<tt>key</tt>对应的value，返回<tt>null</tt>
     * @see Map#put(Object, Object)
     */
    @Nullable
    <V> V put(@NotNull Key<V> key, @NotNull V value);

    /**
     * 如果当前<tt>key</tt>对应的<tt>value</tt>不存在，添加映射，否则不添加。
     *
     * @param key   与指定值关联的键
     * @param value 与指定键关联的值
     * @param <V>   值类型
     * @return 如果当前<tt>key</tt>对应的<tt>value</tt>已经存在，返回存在的<tt>value</tt>，不会进行添加。如果不存在，添加<tt>key</tt>和<tt>value</tt
     * >，返回<tt>null</tt>
     * @see Map#putIfAbsent(Object, Object)
     */
    @Nullable
    <V> V putIfAbsent(@NotNull Key<V> key, @NotNull V value);

    /**
     * 获取指定类型的值。
     *
     * @param key 与指定值关联的键
     * @param <V> 返回值类型
     * @return 指定类型的值，如果没有映射，返回<tt>null</tt>
     * @see Map#get(Object)
     */
    @Nullable
    <V> V get(@NotNull Key<V> key);

    /**
     * 获取指定key的值，如果为null，则取默认值。
     *
     * @param key          与指定值关联的键
     * @param defaultValue 与指定键关联的值如果不存在，返回的默认值
     * @param <V>          返回值类型
     * @return 指定类型的值，如果没有映射，返回<tt>defaultValue</tt>
     * @see Map#getOrDefault(Object, Object)
     */
    <V> V get(@NotNull Key<V> key, V defaultValue);

    /**
     * 获取所有的参数
     *
     * @return 所有参数映射
     */
    Map<Key<?>, Object> getParams();

    /**
     * 获取当前参数映射的大小。
     *
     * @return 当前参数映射的大小
     * @see Map#size()
     */
    int size();

    /**
     * 如果当前map没有映射时，返回<tt>true</tt>，否则返回<tt>false</tt>。
     *
     * @return 如果当前map没有映射时，返回<tt>true</tt>，否则返回<tt>false</tt>
     * @see Map#isEmpty()
     */
    boolean isEmpty();

    /**
     * 是否有指定映射
     *
     * @param key 与指定值关联的键
     * @return 如果当前map有<tt>key</tt>映射时，返回<tt>true</tt>，否则返回<tt>false</tt>
     * @see Map#containsKey(Object)
     */
    boolean containsKey(@NotNull Key<?> key);

    /**
     * 返回是否有指定的值。
     *
     * @param value 与指定值关联的键
     * @return 如果当前map有指定的<tt>value</tt>时，返回<tt>true</tt>，否则返回<tt>false</tt>
     * @see Map#containsValue(Object)
     */
    boolean containsValue(@NotNull Object value);

    /**
     * 删除指定键的映射关系。
     *
     * @param key 与指定值关联的键
     * @param <V> 值类型
     * @return 如果存在映射，返回之前<tt>key</tt>对应的<tt>value</tt>，否则返回<tt>null</tt>
     * @see Map#remove(Object)
     */
    @Nullable
    <V> V remove(@NotNull Key<V> key);

    /**
     * 将<tt>params</tt>中的所有映射添加至当前map。
     *
     * @param params 参数map
     * @see Map#putAll(Map)
     */
    void putAll(@NotNull Map<? extends Key<?>, ?> params);

    /**
     * 删除所有映射。
     */
    void clear();
}

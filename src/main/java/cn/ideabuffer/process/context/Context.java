package cn.ideabuffer.process.context;

import cn.ideabuffer.process.block.Block;
import cn.ideabuffer.process.context.ContextKey;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * 流程上下文
 *
 * @author sangjian.sj
 * @date 2020/01/18
 */
public interface Context {

    /**
     * 获取当前区块
     *
     * @return 当前区块
     */
    Block getBlock();

    <V> void put(ContextKey<V> key, V value);

    /**
     * 获取指定类型的值
     *
     * @param key key
     * @param <V> 返回值类型
     * @return
     */
    <V> V get(ContextKey<V> key);

    /**
     * 获取指定key的值，如果为null，则取默认值
     *
     * @param key          key
     * @param defaultValue 默认值
     * @param <V>          返回值类型
     * @return
     */
    <V> V get(ContextKey<V> key, V defaultValue);

    Map<ContextKey<?>, Object> getParams();

    int size();

    boolean isEmpty();

    <V> boolean containsKey(ContextKey<V> key);

    boolean containsValue(Object value);

    <V> V remove(ContextKey<V> key);

    void putAll(@NotNull Map<? extends ContextKey<?>, ?> params);

    void clear();
}

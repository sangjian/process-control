package cn.ideabuffer.process.core.context;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link Parameter}的实现。
 *
 * @author sangjian.sj
 * @date 2020/03/26
 * @see Parameter
 */
public class ParameterImpl implements Parameter {

    private Map<Key<?>, Object> params;

    public ParameterImpl() {
        this(null);
    }

    public ParameterImpl(@Nullable Map<Key<?>, Object> params) {
        this.params = params == null ? new ConcurrentHashMap<>() : params;
    }

    @Nullable
    @Override
    public <V> V put(@NotNull Key<V> key, @NotNull V value) {
        Object v = params.put(key, value);
        if (v == null) {
            return null;
        }
        //noinspection unchecked
        return (V)v;
    }

    @Nullable
    @Override
    public <V> V putIfAbsent(@NotNull Key<V> key, @NotNull V value) {
        Object v = params.putIfAbsent(key, value);
        if (v == null) {
            return null;
        }
        //noinspection unchecked
        return (V)v;
    }

    @Nullable
    @Override
    public <V> V get(@NotNull Key<V> key) {
        Object value = params.get(key);
        if (value == null) {
            return null;
        }
        //noinspection unchecked
        return (V)value;
    }

    @Override
    public <V> V get(@NotNull Key<V> key, V defaultValue) {
        V value = get(key);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    @Override
    public Map<Key<?>, Object> getParameters() {
        return Collections.unmodifiableMap(params);
    }

    @Override
    public int size() {
        return params.size();
    }

    @Override
    public boolean isEmpty() {
        return params.isEmpty();
    }

    @Override
    public boolean containsKey(@NotNull Key<?> key) {
        return params.containsKey(key);
    }

    @Override
    public boolean containsValue(@NotNull Object value) {
        return params.containsValue(value);
    }

    @Nullable
    @Override
    public <V> V remove(@NotNull Key<V> key) {
        Object v = params.remove(key);
        if (v == null) {
            return null;
        }
        //noinspection unchecked
        return (V)v;
    }

    @Override
    public void putAll(@NotNull Map<? extends Key<?>, ?> params) {
        this.params.putAll(params);
    }

}

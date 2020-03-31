package cn.ideabuffer.process.core.context;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sangjian.sj
 * @date 2020/03/26
 */
public class ParameterImpl implements Parameter {

    private Map<Key<?>, Object> params;

    public ParameterImpl() {
        this(null);
    }

    public ParameterImpl(Map<Key<?>, Object> params) {
        this.params = params == null ? new ConcurrentHashMap<>() : params;
    }

    @Override
    public <V> V put(@NotNull Key<V> key, V value) {
        Object v = params.put(key, value);
        if (v == null) {
            return null;
        }
        //noinspection unchecked
        return (V)v;
    }

    @Override
    public <V> V putIfAbsent(@NotNull Key<V> key, V value) {
        Object v = params.putIfAbsent(key, value);
        if (v == null) {
            return null;
        }
        //noinspection unchecked
        return (V)v;
    }

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
    public Map<Key<?>, Object> getParams() {
        return params;
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
    public <V> boolean containsKey(Key<V> key) {
        return params.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return params.containsValue(value);
    }

    @Override
    public <V> V remove(Key<V> key) {
        return null;
    }

    @Override
    public void putAll(@NotNull Map<? extends Key<?>, ?> params) {
        this.params.putAll(params);
    }

    @Override
    public void clear() {
        params.clear();
    }

}

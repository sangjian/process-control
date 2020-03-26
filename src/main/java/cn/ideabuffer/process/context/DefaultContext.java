package cn.ideabuffer.process.context;

import cn.ideabuffer.process.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sangjian.sj
 * @date 2020/02/07
 */
public class DefaultContext implements Context {

    private Block block;
    private Map<ContextKey<?>, Object> params;

    public DefaultContext() {
        this(null, null);
    }

    public DefaultContext(Block block) {
        this(block, null);
    }

    public DefaultContext(Block block, Map<ContextKey<?>, Object> params) {
        this.block = block == null ? new Block() : block;
        this.params = params == null ? new ConcurrentHashMap<>() : params;
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public <V> V put(@NotNull ContextKey<V> key, V value) {
        Object v = params.put(key, value);
        if(v == null) {
            return null;
        }
        //noinspection unchecked
        return (V)v;
    }

    @Override
    public <V> V putIfAbsent(@NotNull ContextKey<V> key, V value) {
        Object v = params.putIfAbsent(key, value);
        if (v == null) {
            return null;
        }
        //noinspection unchecked
        return (V)v;
    }

    @Override
    public <V> V get(@NotNull ContextKey<V> key) {
        Object value = params.get(key);
        if (value == null) {
            return null;
        }
        //noinspection unchecked
        return (V)value;
    }

    @Override
    public <V> V get(@NotNull ContextKey<V> key, V defaultValue) {
        V value = get(key);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    @Override
    public Map<ContextKey<?>, Object> getParams() {
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
    public <V> boolean containsKey(ContextKey<V> key) {
        return params.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return params.containsValue(value);
    }

    @Override
    public <V> V remove(ContextKey<V> key) {
        return null;
    }

    @Override
    public void putAll(@NotNull Map<? extends ContextKey<?>, ?> params) {
        this.params.putAll(params);
    }

    @Override
    public void clear() {
        params.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        if (!super.equals(o)) { return false; }
        DefaultContext that = (DefaultContext)o;
        return Objects.equals(block, that.block);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), block);
    }
}

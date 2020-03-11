package cn.ideabuffer.process;

import cn.ideabuffer.process.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author sangjian.sj
 * @date 2020/02/15
 */
public class ContextWrapper implements Context {

    private Context context;

    private Block block;

    public ContextWrapper(Context context, Block block) {
        this.context = context;
        this.block = block;
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public <V> V get(Object key, Class<V> valueType) {
        return context.get(key, valueType);
    }

    @Override
    public <V> V get(Object key, V defaultValue) {
        return context.get(key, defaultValue);
    }

    @Override
    public int size() {return context.size();}

    @Override
    public boolean isEmpty() {return context.isEmpty();}

    @Override
    public boolean containsKey(Object key) {return context.containsKey(key);}

    @Override
    public boolean containsValue(Object value) {return context.containsValue(value);}

    @Override
    public Object get(Object key) {return context.get(key);}

    @Nullable
    @Override
    public Object put(Object key, Object value) {return context.put(key, value);}

    @Override
    public Object remove(Object key) {return context.remove(key);}

    @Override
    public void putAll(@NotNull Map<?, ?> m) {context.putAll(m);}

    @Override
    public void clear() {context.clear();}

    @NotNull
    @Override
    public Set<Object> keySet() {return context.keySet();}

    @NotNull
    @Override
    public Collection<Object> values() {return context.values();}

    @NotNull
    @Override
    public Set<Entry<Object, Object>> entrySet() {return context.entrySet();}

    @Override
    public boolean equals(Object o) {return context.equals(o);}

    @Override
    public int hashCode() {return context.hashCode();}

    @Override
    public Object getOrDefault(Object key, Object defaultValue) {return context.getOrDefault(key, defaultValue);}

    @Override
    public void forEach(BiConsumer<? super Object, ? super Object> action) {context.forEach(action);}

    @Override
    public void replaceAll(BiFunction<? super Object, ? super Object, ?> function) {context.replaceAll(function);}

    @Nullable
    @Override
    public Object putIfAbsent(Object key, Object value) {return context.putIfAbsent(key, value);}

    @Override
    public boolean remove(Object key, Object value) {return context.remove(key, value);}

    @Override
    public boolean replace(Object key, Object oldValue, Object newValue) {
        return context.replace(key, oldValue, newValue);
    }

    @Nullable
    @Override
    public Object replace(Object key, Object value) {return context.replace(key, value);}

    @Override
    public Object computeIfAbsent(Object key, Function<? super Object, ?> mappingFunction) {
        return context.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public Object computeIfPresent(Object key,
        BiFunction<? super Object, ? super Object, ?> remappingFunction) {
        return context.computeIfPresent(key, remappingFunction);
    }

    @Override
    public Object compute(Object key,
        BiFunction<? super Object, ? super Object, ?> remappingFunction) {
        return context.compute(key, remappingFunction);
    }

    @Override
    public Object merge(Object key, Object value,
        BiFunction<? super Object, ? super Object, ?> remappingFunction) {
        return context.merge(key, value, remappingFunction);
    }
}

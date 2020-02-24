package cn.ideabuffer.process.block;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author sangjian.sj
 * @date 2020/02/22
 */
public class BlockFacade implements Block {

    private Block block;

    public BlockFacade(Block block) {
        this.block = block;
    }
    @Override
    public <K, V> V get(K key, V defaultValue) {return block.get(key, defaultValue);}

    @Override
    public Block parent() {return block.parent();}

    @Override
    public boolean allowBreak() {return block.allowBreak();}

    @Override
    public boolean allowContinue() {return block.allowContinue();}

    @Override
    public void doBreak() {
        block.doBreak();
    }

    @Override
    public void doContinue() {
        block.doContinue();
    }

    @Override
    public boolean breakable() {
        return block.breakable();
    }

    @Override
    public boolean continuable() {
        return block.continuable();
    }

    @Override
    public int size() {
        return block.size();
    }

    @Override
    public boolean isEmpty() {
        return block.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return block.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return block.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return block.get(key);
    }

    @Override
    public Object put(Object key, Object value) {
        return block.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return block.remove(key);
    }

    @Override
    public void putAll(Map<?, ?> m) {
        block.putAll(m);
    }

    @Override
    public void clear() {
        block.clear();
    }

    @Override
    public Set<Object> keySet() {
        return block.keySet();
    }

    @Override
    public Collection<Object> values() {
        return block.values();
    }

    @Override
    public Set<Entry<Object, Object>> entrySet() {
        return block.entrySet();
    }

    @Override
    public boolean equals(Object o) {
        return block.equals(o);
    }

    @Override
    public int hashCode() {
        return block.hashCode();
    }

    @Override
    public Object getOrDefault(Object key, Object defaultValue) {
        return block.getOrDefault(key, defaultValue);
    }

    @Override
    public void forEach(BiConsumer<? super Object, ? super Object> action) {
        block.forEach(action);
    }

    @Override
    public void replaceAll(BiFunction<? super Object, ? super Object, ?> function) {
        block.replaceAll(function);
    }

    @Override
    public Object putIfAbsent(Object key, Object value) {
        return block.putIfAbsent(key, value);
    }

    @Override
    public boolean remove(Object key, Object value) {
        return block.remove(key, value);
    }

    @Override
    public boolean replace(Object key, Object oldValue, Object newValue) {
        return block.replace(key, oldValue, newValue);
    }

    @Override
    public Object replace(Object key, Object value) {
        return block.replace(key, value);
    }

    @Override
    public Object computeIfAbsent(Object key, Function<? super Object, ?> mappingFunction) {
        return block.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public Object computeIfPresent(Object key,
        BiFunction<? super Object, ? super Object, ?> remappingFunction) {
        return block.computeIfPresent(key, remappingFunction);
    }

    @Override
    public Object compute(Object key,
        BiFunction<? super Object, ? super Object, ?> remappingFunction) {return block.compute(key, remappingFunction);}

    @Override
    public Object merge(Object key, Object value,
        BiFunction<? super Object, ? super Object, ?> remappingFunction) {
        return block.merge(key, value, remappingFunction);
    }
}

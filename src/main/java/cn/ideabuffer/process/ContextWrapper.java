package cn.ideabuffer.process;

import cn.ideabuffer.process.block.Block;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

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
    public <K, V> V get(K key, V defaultValue) {
        return context.get(key, defaultValue);
    }

    @Override
    public int size() {
        return context.size();
    }

    @Override
    public boolean isEmpty() {
        return context.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return context.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return context.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return context.get(key);
    }

    @Override
    public Object put(Object key, Object value) {
        return context.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return context.remove(key);
    }

    @Override
    public void putAll(Map<?, ?> m) {
        context.putAll(m);
    }

    @Override
    public void clear() {
        context.clear();
    }

    @Override
    public Set<Object> keySet() {
        return context.keySet();
    }

    @Override
    public Collection<Object> values() {
        return context.values();
    }

    @Override
    public Set<Entry<Object, Object>> entrySet() {
        return context.entrySet();
    }
}

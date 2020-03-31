package cn.ideabuffer.process.core.context;

import cn.ideabuffer.process.core.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

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
    public <V> V put(@NotNull Key<V> key, V value) {return context.put(key, value);}

    @Override
    public <V> V putIfAbsent(@NotNull Key<V> key, V value) {return context.putIfAbsent(key, value);}

    @Override
    public <V> V get(@NotNull Key<V> key) {return context.get(key);}

    @Override
    public <V> V get(@NotNull Key<V> key, V defaultValue) {return context.get(key, defaultValue);}

    @Override
    public Map<Key<?>, Object> getParams() {return context.getParams();}

    @Override
    public int size() {return context.size();}

    @Override
    public boolean isEmpty() {return context.isEmpty();}

    @Override
    public boolean containsKey(Key<?> key) {return context.containsKey(key);}

    @Override
    public boolean containsValue(Object value) {return context.containsValue(value);}

    @Override
    public <V> V remove(Key<V> key) {return context.remove(key);}

    @Override
    public void putAll(
        @NotNull Map<? extends Key<?>, ?> params) {context.putAll(params);}

    @Override
    public void clear() {context.clear();}

}

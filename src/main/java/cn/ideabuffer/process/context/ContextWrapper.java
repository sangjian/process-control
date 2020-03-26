package cn.ideabuffer.process.context;

import cn.ideabuffer.process.block.Block;
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
    public <V> V put(@NotNull ContextKey<V> key, V value) {return context.put(key, value);}

    @Override
    public <V> V putIfAbsent(@NotNull ContextKey<V> key, V value) {return context.putIfAbsent(key, value);}

    @Override
    public <V> V get(@NotNull ContextKey<V> key) {return context.get(key);}

    @Override
    public <V> V get(@NotNull ContextKey<V> key, V defaultValue) {return context.get(key, defaultValue);}

    @Override
    public Map<ContextKey<?>, Object> getParams() {return context.getParams();}

    @Override
    public int size() {return context.size();}

    @Override
    public boolean isEmpty() {return context.isEmpty();}

    @Override
    public <V> boolean containsKey(ContextKey<V> key) {return context.containsKey(key);}

    @Override
    public boolean containsValue(Object value) {return context.containsValue(value);}

    @Override
    public <V> V remove(ContextKey<V> key) {return context.remove(key);}

    @Override
    public void putAll(
        @NotNull Map<? extends ContextKey<?>, ?> params) {context.putAll(params);}

    @Override
    public void clear() {context.clear();}

}

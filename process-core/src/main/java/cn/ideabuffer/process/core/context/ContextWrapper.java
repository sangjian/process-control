package cn.ideabuffer.process.core.context;

import cn.ideabuffer.process.core.block.Block;
import cn.ideabuffer.process.core.block.BlockWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * @author sangjian.sj
 * @date 2020/02/15
 */
public class ContextWrapper implements Context {

    private Context context;

    private Block block;

    private KeyMapper mapper;

    public ContextWrapper(Context context, Block block) {
        this(context, block, null);
    }

    public ContextWrapper(Context context, Block block, KeyMapper mapper) {
        this.context = context;
        this.block = new BlockWrapper(block, mapper);
        this.mapper = mapper;
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public Context cloneContext() {
        return context.cloneContext();
    }

    private boolean hasMapping() {
        return mapper != null && !mapper.isEmpty();
    }

    @Override
    public <V> V put(@NotNull Key<V> key, V value) {
        Key<V> mappingKey;
        if (hasMapping() && (mappingKey = mapper.getMappingKey(key)) != null) {
            return context.put(mappingKey, value);
        }
        return context.put(key, value);
    }

    @Override
    public <V> V putIfAbsent(@NotNull Key<V> key, V value) {
        Key<V> mappingKey;
        if (hasMapping() && (mappingKey = mapper.getMappingKey(key)) != null) {
            return context.putIfAbsent(mappingKey, value);
        }
        return context.putIfAbsent(key, value);
    }

    @Override
    public <V> V get(@NotNull Key<V> key) {
        Key<V> mappingKey;
        if (hasMapping() && (mappingKey = mapper.getMappingKey(key)) != null) {
            return context.get(mappingKey);
        }
        return context.get(key);
    }

    @Override
    public <V> V get(@NotNull Key<V> key, V defaultValue) {
        Key<V> mappingKey;
        if (hasMapping() && (mappingKey = mapper.getMappingKey(key)) != null) {
            return context.get(mappingKey, defaultValue);
        }
        return context.get(key, defaultValue);
    }

    @Override
    public Map<Key<?>, Object> getParams() {return context.getParams();}

    @Override
    public int size() {return context.size();}

    @Override
    public boolean isEmpty() {return context.isEmpty();}

    @Override
    public boolean containsKey(Key<?> key) {
        Key<?> mappingKey;
        if (hasMapping() && (mappingKey = mapper.getMappingKey(key)) != null) {
            return context.containsKey(mappingKey);
        }
        return context.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {return context.containsValue(value);}

    @Override
    public <V> V remove(Key<V> key) {
        Key<V> mappingKey;
        if (hasMapping() && (mappingKey = mapper.getMappingKey(key)) != null) {
            return context.remove(mappingKey);
        }
        return context.remove(key);
    }

    @Override
    public void putAll(
        @NotNull Map<? extends Key<?>, ?> params) {
        context.putAll(params);
    }

    @Override
    public void clear() {context.clear();}

}

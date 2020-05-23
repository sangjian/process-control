package cn.ideabuffer.process.core.block;

import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.context.KeyMapper;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * @author sangjian.sj
 * @date 2020/02/22
 */
public class BlockWrapper extends Block {

    private Block block;

    private KeyMapper mapper;

    public BlockWrapper(Block block) {
        this(block, null);
    }

    public BlockWrapper(Block block, KeyMapper mapper) {
        this.block = block;
        this.mapper = mapper;
    }

    @Override
    public boolean breakable() {return block.breakable();}

    @Override
    public boolean continuable() {return block.continuable();}

    @Override
    public boolean hasBroken() {return block.hasBroken();}

    @Override
    public boolean hasContinued() {return block.hasContinued();}

    @Override
    public void resetBreak() {block.resetBreak();}

    @Override
    public void resetContinue() {block.resetContinue();}

    @Override
    public boolean equals(Object o) {return block.equals(o);}

    @Override
    public int hashCode() {return block.hashCode();}

    private boolean hasMapping() {
        return mapper != null && !mapper.isEmpty();
    }

    @Override
    public <V> V put(@NotNull Key<V> key, V value) {
        Key<V> mappingKey;
        if (hasMapping() && (mappingKey = mapper.getMappingKey(key)) != null) {
            return block.put(mappingKey, value);
        }
        return block.put(key, value);
    }

    @Override
    public <V> V putIfAbsent(@NotNull Key<V> key, V value) {
        Key<V> mappingKey;
        if (hasMapping() && (mappingKey = mapper.getMappingKey(key)) != null) {
            return block.putIfAbsent(mappingKey, value);
        }
        return block.putIfAbsent(key, value);
    }

    @Override
    public <V> V get(@NotNull Key<V> key) {
        Key<V> mappingKey;
        if (hasMapping() && (mappingKey = mapper.getMappingKey(key)) != null) {
            return block.get(mappingKey);
        }
        return block.get(key);
    }

    @Override
    public <V> V get(@NotNull Key<V> key, V defaultValue) {
        Key<V> mappingKey;
        if (hasMapping() && (mappingKey = mapper.getMappingKey(key)) != null) {
            return block.get(mappingKey, defaultValue);
        }
        return block.get(key, defaultValue);
    }

    @Override
    public Map<Key<?>, Object> getParams() {
        return block.getParams();
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
    public boolean containsKey(Key<?> key) {
        Key<?> mappingKey;
        if (hasMapping() && (mappingKey = mapper.getMappingKey(key)) != null) {
            return block.containsKey(mappingKey);
        }
        return block.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return block.containsValue(value);
    }

    @Override
    public <V> V remove(Key<V> key) {
        Key<V> mappingKey;
        if (hasMapping() && (mappingKey = mapper.getMappingKey(key)) != null) {
            return block.remove(mappingKey);
        }
        return block.remove(key);
    }

    @Override
    public void putAll(@NotNull Map<? extends Key<?>, ?> params) {
        block.putAll(params);
    }

    @Override
    public void clear() {
        block.clear();
    }
}

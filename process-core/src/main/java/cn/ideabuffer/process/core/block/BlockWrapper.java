package cn.ideabuffer.process.core.block;

import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.context.KeyMapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * {@link Block}对象的一个包装，增加了key映射的功能。
 *
 * @author sangjian.sj
 * @date 2020/02/22
 * @see KeyMapper
 */
public class BlockWrapper implements Block {

    /**
     * 被包装的block
     */
    private Block block;

    /**
     * 映射器
     */
    private KeyMapper mapper;

    public BlockWrapper(Block block) {
        this(block, null);
    }

    public BlockWrapper(Block block, KeyMapper mapper) {
        this.block = block;
        this.mapper = mapper;
    }

    @Override
    public boolean allowBreak() {
        return block.allowBreak();
    }

    @Override
    public boolean allowContinue() {
        return block.allowContinue();
    }

    @Override
    public void doBreak() {
        block.doBreak();
    }

    @Override
    public void doContinue() {
        block.doContinue();
    }

    @Override
    public boolean hasBroken() {
        return block.hasBroken();
    }

    @Override
    public boolean hasContinued() {
        return block.hasContinued();
    }

    @Override
    public Block getParent() {
        return block.getParent();
    }

    @Override
    public boolean equals(Object o) {return block.equals(o);}

    @Override
    public int hashCode() {return block.hashCode();}

    private <V> Key<V> getMappingKey(Key<V> key) {
        if (mapper == null || mapper.isEmpty()) {
            return null;
        }
        return mapper.getMappingKey(key);
    }

    @Nullable
    @Override
    public <V> V put(@NotNull Key<V> key, @NotNull V value) {
        Key<V> mappingKey = getMappingKey(key);
        if (mappingKey != null) {
            return block.put(mappingKey, value);
        }
        return block.put(key, value);
    }

    @Nullable
    @Override
    public <V> V putIfAbsent(@NotNull Key<V> key, @NotNull V value) {
        Key<V> mappingKey = getMappingKey(key);
        if (mappingKey != null) {
            return block.putIfAbsent(mappingKey, value);
        }
        return block.putIfAbsent(key, value);
    }

    @Nullable
    @Override
    public <V> V get(@NotNull Key<V> key) {
        Key<V> mappingKey = getMappingKey(key);
        if (mappingKey != null) {
            return block.get(mappingKey);
        }
        return block.get(key);
    }

    @Override
    public <V> V get(@NotNull Key<V> key, V defaultValue) {
        Key<V> mappingKey = getMappingKey(key);
        if (mappingKey != null) {
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
    public boolean containsKey(@NotNull Key<?> key) {
        Key<?> mappingKey = getMappingKey(key);
        if (mappingKey != null) {
            return block.containsKey(mappingKey);
        }
        return block.containsKey(key);
    }

    @Override
    public boolean containsValue(@NotNull Object value) {
        return block.containsValue(value);
    }

    @Nullable
    @Override
    public <V> V remove(@NotNull Key<V> key) {
        Key<V> mappingKey = getMappingKey(key);
        if (mappingKey != null) {
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

package cn.ideabuffer.process.core.context;

import cn.ideabuffer.process.core.KeyManager;
import cn.ideabuffer.process.core.block.Block;
import cn.ideabuffer.process.core.block.BlockFacade;
import cn.ideabuffer.process.core.exceptions.UnreadableKeyException;
import cn.ideabuffer.process.core.exceptions.UnwritableKeyException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * context包装类，在新创建作用域时，将当前context与新创建的block进行包装，这样可以实现不同作用域的功能。同时还会有参数映射、权限校验的功能。
 *
 * @author sangjian.sj
 * @date 2020/02/15
 * @see Context
 * @see Block
 * @see KeyMapper
 */
public class ContextWrapper implements Context {

    /**
     * 被包装的context
     */
    private Context context;

    /**
     * 新作用域的block
     */
    private Block block;

    /**
     * 参数映射器
     */
    @Nullable
    private KeyMapper mapper;

    /**
     * 可读key
     */
    @Nullable
    private Set<Key<?>> readableKeys;

    /**
     * 可写key
     */
    @Nullable
    private Set<Key<?>> writableKeys;

    public ContextWrapper(@NotNull Context context, @NotNull Block block) {
        this(context, block, null);
    }

    public ContextWrapper(@NotNull Context context, @NotNull Block block, @Nullable KeyManager keyManager) {
        KeyMapper mapper = keyManager == null ? null : keyManager.getKeyMapper();

        this.context = context;
        this.mapper = mapper;
        // 如果没有设置mapper，取上一级的mapper
        if (mapper == null) {
            this.mapper = context.getMapper();
        }
        this.block = new BlockFacade(block);
        this.readableKeys = keyManager == null ? null : keyManager.getReadableKeys();
        this.writableKeys = keyManager == null ? null : keyManager.getWritableKeys();
    }

    public ContextWrapper(@NotNull Context context, @NotNull Block block, @Nullable KeyMapper mapper,
        @Nullable Set<Key<?>> readableKeys,
        @Nullable Set<Key<?>> writableKeys) {
        this.context = context;
        this.mapper = mapper;
        // 如果没有设置mapper，取上一级的mapper
        if (mapper == null) {
            this.mapper = context.getMapper();
        }
        this.block = new BlockFacade(block);
        this.readableKeys = readableKeys;
        this.writableKeys = writableKeys;
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public Context cloneContext() {
        if (mapper == null || mapper.isEmpty()) {
            return context.cloneContext();
        }
        Context ctx = new ContextImpl(null, context.getParameters());
        Block block = new BlockFacade(ctx.getBlock());
        return Contexts.wrap(ctx, block, mapper);
    }

    @Nullable
    @Override
    public <V> V put(@NotNull Key<V> key, @NotNull V value) {
        return put(key, value, true, true);
    }

    @Nullable
    private <V> V put(@NotNull Key<V> key, V value, boolean keyCheck, boolean keyMapping) {
        Key<V> k = key;
        if (keyMapping) {
            Key<V> mappingKey = Contexts.getMappingKey(key, this.mapper);
            if (mappingKey != null) {
                k = mappingKey;
            }
        }
        if (keyCheck && !writableKey(k)) {
            throw new UnwritableKeyException(k + " is not writable, check the registration of the key!");
        }
        if (context instanceof ContextWrapper) {
            return ((ContextWrapper)context).put(k, value, false, false);
        }
        return context.put(k, value);
    }

    @Nullable
    @Override
    public <V> V putIfAbsent(@NotNull Key<V> key, @NotNull V value) {
        return putIfAbsent(key, value, true, true);
    }

    @Nullable
    private <V> V putIfAbsent(@NotNull Key<V> key, @NotNull V value, boolean keyCheck, boolean keyMapping) {
        Key<V> k = key;
        if (keyMapping) {
            Key<V> mappingKey = Contexts.getMappingKey(key, this.mapper);
            if (mappingKey != null) {
                k = mappingKey;
            }
        }
        if (keyCheck && !writableKey(k)) {
            throw new UnwritableKeyException(k + " is not writable, check the registration of the key!");
        }
        if (context instanceof ContextWrapper) {
            return ((ContextWrapper)context).putIfAbsent(k, value, false, false);
        }
        return context.putIfAbsent(k, value);
    }

    @Nullable
    @Override
    public <V> V get(@NotNull Key<V> key) {
        return get(key, true, true);
    }

    @Nullable
    protected <V> V get(@NotNull Key<V> key, boolean keyCheck, boolean keyMapping) {
        return get(key, null, keyCheck, keyMapping);
    }

    @Override
    public <V> V get(@NotNull Key<V> key, V defaultValue) {
        return get(key, defaultValue, true, true);
    }

    @NotNull
    @Override
    public Map<Key<?>, Object> getParameters() {
        return context.getParameters();
    }

    private <V> V get(@NotNull Key<V> key, V defaultValue, boolean keyCheck, boolean keyMapping) {
        Key<V> k = key;
        if (keyMapping) {
            Key<V> mappingKey = Contexts.getMappingKey(key, this.mapper);
            if (mappingKey != null) {
                k = mappingKey;
            }
        }
        if (keyCheck && !readableKey(k)) {
            throw new UnreadableKeyException(k + " is not readable, check the registration of the key!");
        }
        if (context instanceof ContextWrapper) {
            return ((ContextWrapper)context).get(k, defaultValue, false, false);
        }
        return context.get(k, defaultValue);
    }

    @Override
    public int size() {return context.size();}

    @Override
    public boolean isEmpty() {return context.isEmpty();}

    @Override
    public boolean containsKey(@NotNull Key<?> key) {
        return containsKey(key, true, true);
    }

    private boolean containsKey(@NotNull Key<?> key, boolean keyCheck, boolean keyMapping) {
        Key<?> k = key;
        if (keyMapping) {
            Key<?> mappingKey = Contexts.getMappingKey(key, this.mapper);
            if (mappingKey != null) {
                k = mappingKey;
            }
        }
        if (keyCheck && !readableKey(k)) {
            throw new UnreadableKeyException(k + " is not readable, check the registration of the key!");
        }
        if (context instanceof ContextWrapper) {
            return ((ContextWrapper)context).containsKey(k, false, false);
        }
        return context.containsKey(k);
    }

    @Override
    public boolean containsValue(@NotNull Object value) {return context.containsValue(value);}

    @Nullable
    @Override
    public <V> V remove(@NotNull Key<V> key) {
        return remove(key, true, true);
    }

    @Nullable
    private <V> V remove(Key<V> key, boolean keyCheck, boolean keyMapping) {
        Key<V> k = key;
        if (keyMapping) {
            Key<V> mappingKey = Contexts.getMappingKey(key, this.mapper);
            if (mappingKey != null) {
                k = mappingKey;
            }
        }
        if (keyCheck && !writableKey(k)) {
            throw new UnwritableKeyException(k + " is not writable, check the registration of the key!");
        }
        if (context instanceof ContextWrapper) {
            return ((ContextWrapper)context).remove(k, false, false);
        }
        return context.remove(k);
    }

    @Override
    public void putAll(
        @NotNull Map<? extends Key<?>, ?> params) {
        putAll(params, true, true);
    }

    private void putAll(@NotNull Map<? extends Key<?>, ?> params, boolean keyCheck, boolean keyMapping) {
        if (params.isEmpty()) {
            return;
        }
        Map<Key<?>, Object> map = new HashMap<>(params.size());
        for (Map.Entry<? extends Key<?>, ?> entry : params.entrySet()) {
            Key<?> key = entry.getKey();
            Key<?> k = key;
            if (keyMapping) {
                Key<?> mappingKey = Contexts.getMappingKey(key, this.mapper);
                if (mappingKey != null) {
                    k = mappingKey;
                }
            }
            if (keyCheck && !writableKey(k)) {
                throw new UnwritableKeyException(k + " is not writable, check the registration of the key!");
            }
            map.put(k, entry.getValue());
        }
        if (context instanceof ContextWrapper) {
            ((ContextWrapper)context).putAll(map, false, false);
        } else {
            context.putAll(map);
        }
    }

    @Override
    public boolean readableKey(Key<?> key) {
        if (readableKeys == null || readableKeys.isEmpty()) {
            return false;
        }
        return readableKeys.contains(key);
    }

    @Override
    public boolean writableKey(Key<?> key) {
        if (writableKeys == null || writableKeys.isEmpty()) {
            return false;
        }
        return writableKeys.contains(key);
    }

    @Override
    public Throwable getCurrentException() {
        return context.getCurrentException();
    }

    @Override
    public void setCurrentException(Throwable t) {
        context.setCurrentException(t);
    }

    @Override
    public KeyMapper getMapper() {
        return this.mapper;
    }
}

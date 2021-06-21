package cn.ideabuffer.process.core.context;

import cn.ideabuffer.process.core.ProcessDefinition;
import cn.ideabuffer.process.core.block.Block;
import cn.ideabuffer.process.core.block.BlockWrapper;
import cn.ideabuffer.process.core.exception.UnreadableKeyException;
import cn.ideabuffer.process.core.exception.UnwritableKeyException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    private KeyMapper mapper;

    /**
     * 可读key
     */
    private Set<Key<?>> readableKeys;

    /**
     * 可写key
     */
    private Set<Key<?>> writableKeys;

    public ContextWrapper(Context context, Block block) {
        this(context, block, null);
    }

    public ContextWrapper(Context context, Block block, KeyMapper mapper) {
        this(context, block, mapper, null, null);
    }

    public ContextWrapper(Context context, Block block, KeyMapper mapper, Set<Key<?>> readableKeys,
        Set<Key<?>> writableKeys) {
        this.context = context;
        this.block = new BlockWrapper(block, mapper);
        this.mapper = mapper;
        this.readableKeys = readableKeys;
        this.writableKeys = writableKeys;
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public Context cloneContext() {
        return context.cloneContext();
    }

    private <V> Key<V> getMappingKey(Key<V> key) {
        if (mapper == null || mapper.isEmpty()) {
            return null;
        }
        return mapper.getMappingKey(key);
    }

    @Nullable
    @Override
    public <V> V put(@NotNull Key<V> key, @NotNull V value) {
        return put(key, value, true);
    }

    @Nullable
    protected <V> V put(@NotNull Key<V> key, V value, boolean keyCheck) {
        Key<V> k = key;
        Key<V> mappingKey = getMappingKey(key);
        if (mappingKey != null) {
            k = mappingKey;
        }
        if (keyCheck && !writableKey(k)) {
            throw new UnwritableKeyException(k + " is not writable, check the registration of the key!");
        }
        if (context instanceof ContextWrapper) {
            return ((ContextWrapper)context).put(k, value, false);
        }
        return context.put(k, value);
    }

    @Nullable
    @Override
    public <V> V putIfAbsent(@NotNull Key<V> key, @NotNull V value) {
        return putIfAbsent(key, value, true);
    }

    @Nullable
    protected <V> V putIfAbsent(@NotNull Key<V> key, @NotNull V value, boolean keyCheck) {
        Key<V> k = key;
        Key<V> mappingKey = getMappingKey(key);
        if (mappingKey != null) {
            k = mappingKey;
        }
        if (keyCheck && !writableKey(k)) {
            throw new UnwritableKeyException(k + " is not writable, check the registration of the key!");
        }
        if (context instanceof ContextWrapper) {
            return ((ContextWrapper)context).putIfAbsent(k, value, false);
        }
        return context.putIfAbsent(k, value);
    }

    @Nullable
    @Override
    public <V> V get(@NotNull Key<V> key) {
        return get(key, true);
    }

    @Nullable
    protected <V> V get(@NotNull Key<V> key, boolean keyCheck) {
        return get(key, null, keyCheck);
    }

    @Override
    public <V> V get(@NotNull Key<V> key, V defaultValue) {
        return get(key, defaultValue, true);
    }

    protected <V> V get(@NotNull Key<V> key, V defaultValue, boolean keyCheck) {
        Key<V> k = key;
        Key<V> mappingKey = getMappingKey(key);
        if (mappingKey != null) {
            k = mappingKey;
        }
        if (keyCheck && !readableKey(k)) {
            throw new UnreadableKeyException(k + " is not readable, check the registration of the key!");
        }
        if (context instanceof ContextWrapper) {
            return ((ContextWrapper)context).get(k, defaultValue, false);
        }
        return context.get(k, defaultValue);
    }

    @Override
    public Map<Key<?>, Object> getParams() {return context.getParams();}

    @Override
    public int size() {return context.size();}

    @Override
    public boolean isEmpty() {return context.isEmpty();}

    @Override
    public boolean containsKey(@NotNull Key<?> key) {
        Key<?> k = key;
        Key<?> mappingKey = getMappingKey(key);
        if (mappingKey != null) {
            k = mappingKey;
        }
        return context.containsKey(k);
    }

    @Override
    public boolean containsValue(@NotNull Object value) {return context.containsValue(value);}

    @Nullable
    @Override
    public <V> V remove(@NotNull Key<V> key) {
        return remove(key, true);
    }

    @Nullable
    protected <V> V remove(Key<V> key, boolean keyCheck) {
        Key<V> k = key;
        Key<V> mappingKey = getMappingKey(key);
        if (mappingKey != null) {
            k = mappingKey;
        }
        if (keyCheck && !writableKey(k)) {
            throw new UnwritableKeyException(k + " is not writable, check the registration of the key!");
        }
        if (context instanceof ContextWrapper) {
            return ((ContextWrapper)context).remove(k, false);
        }
        return context.remove(k);
    }

    @Override
    public void putAll(
        @NotNull Map<? extends Key<?>, ?> params) {
        context.putAll(params);
    }

    @Override
    public void clear() {context.clear();}

    @Override
    public <V> Key<V> getResultKey() {
        return context.getResultKey();
    }

    @Override
    public void setResultKey(ProcessDefinition<?> definition) {
        context.setResultKey(definition);
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
    public Exception getCurrentException() {
        return context.getCurrentException();
    }

    @Override
    public void setCurrentException(Exception e) {
        context.setCurrentException(e);
    }
}

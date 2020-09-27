package cn.ideabuffer.process.core.context;

import cn.ideabuffer.process.core.ProcessDefinition;
import cn.ideabuffer.process.core.block.Block;
import cn.ideabuffer.process.core.block.BlockFacade;
import cn.ideabuffer.process.core.exception.UnreadableKeyException;
import cn.ideabuffer.process.core.exception.KeyNotWritableException;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;

/**
 * @author sangjian.sj
 * @date 2020/02/15
 */
public class ContextWrapper implements Context {

    private Context context;
    private Block block;
    private KeyMapper mapper;
    private Set<Key<?>> readableKeys;
    private Set<Key<?>> writableKeys;

    public ContextWrapper(Context context, Block block) {
        this(context, block, null);
    }

    public ContextWrapper(Context context, Block block, KeyMapper mapper) {
        this(context, block, mapper, null, null);
    }

    public ContextWrapper(Context context, Block block, KeyMapper mapper, Set<Key<?>> readableKeys, Set<Key<?>> writableKeys) {
        this.context = context;
        this.block = new BlockFacade(block, mapper);
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

    @Override
    public <V> V put(@NotNull Key<V> key, @NotNull V value) {
        return put(key, value, true);
    }

    protected <V> V put(@NotNull Key<V> key, V value, boolean keyCheck) {
        Key<V> k = key;
        if (keyCheck && !writableKey(k)) {
            throw new KeyNotWritableException(k + " is not writable, check the registration of the key!");
        }
        Key<V> mappingKey = getMappingKey(key);
        if (mappingKey != null) {
            k = mappingKey;
        }
        if (context instanceof ContextWrapper) {
            return ((ContextWrapper)context).put(k, value, false);
        }
        return context.put(k, value);
    }

    @Override
    public <V> V putIfAbsent(@NotNull Key<V> key, @NotNull V value) {
        return putIfAbsent(key, value, true);
    }

    protected <V> V putIfAbsent(@NotNull Key<V> key, @NotNull V value, boolean keyCheck) {
        if (keyCheck && !writableKey(key)) {
            throw new KeyNotWritableException(key + " is not writable, check the registration of the key!");
        }
        Key<V> k = key;
        Key<V> mappingKey = getMappingKey(key);
        if (mappingKey != null) {
            k = mappingKey;
        }
        if (context instanceof ContextWrapper) {
            return ((ContextWrapper)context).putIfAbsent(k, value, false);
        }
        return context.putIfAbsent(key, value);
    }

    @Override
    public <V> V get(@NotNull Key<V> key) {
        return get(key, true);
    }

    protected <V> V get(@NotNull Key<V> key, boolean keyCheck) {
        return get(key, null, keyCheck);
    }

    @Override
    public <V> V get(@NotNull Key<V> key, V defaultValue) {
        return get(key, defaultValue, true);
    }

    protected <V> V get(@NotNull Key<V> key, V defaultValue, boolean keyCheck) {
        if (keyCheck && !readableKey(key)) {
            throw new UnreadableKeyException(key + " is not readable, check the registration of the key!");
        }
        Key<V> k = key;
        Key<V> mappingKey = getMappingKey(key);
        if (mappingKey != null) {
            k = mappingKey;
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

    @Override
    public <V> V remove(@NotNull Key<V> key) {
        return remove(key, true);
    }

    protected <V> V remove(Key<V> key, boolean keyCheck) {
        Key<V> k = key;
        Key<V> mappingKey = getMappingKey(key);
        if (mappingKey != null) {
            k = mappingKey;
        }
        if (keyCheck && !writableKey(k)) {
            throw new KeyNotWritableException(k + " is not writable, check the registration of the key!");
        }
        if (context instanceof ContextWrapper) {
            return ((ContextWrapper)context).remove(k, false);
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

    @Override
    public void setResultKey(ProcessDefinition<?> definition) {
        context.setResultKey(definition);
    }

    @Override
    public <V> Key<V> getResultKey() {
        return context.getResultKey();
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

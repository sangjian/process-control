package cn.ideabuffer.process.core.context;

import cn.ideabuffer.process.core.ProcessDefinition;
import cn.ideabuffer.process.core.block.Block;
import cn.ideabuffer.process.core.block.BlockFacade;
import cn.ideabuffer.process.core.exception.KeyNotRegisteredException;
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

    private Set<Key<?>> requiredKeys;

    public ContextWrapper(Context context, Block block) {
        this(context, block, null);
    }

    public ContextWrapper(Context context, Block block, KeyMapper mapper) {
        this(context, block, mapper, null);
    }

    public ContextWrapper(Context context, Block block, KeyMapper mapper, Set<Key<?>> requiredKeys) {
        this.context = context;
        this.block = new BlockFacade(block, mapper);
        this.mapper = mapper;
        this.requiredKeys = requiredKeys;
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
    public <V> V put(@NotNull Key<V> key, V value) {
        return put(key, value, true);
    }

    protected <V> V put(@NotNull Key<V> key, V value, boolean keyCheck) {
        Key<V> k = key;
        Key<V> mappingKey = getMappingKey(key);
        if (mappingKey != null) {
            k = mappingKey;
        }
        if (keyCheck && !keyAvailable(k)) {
            throw new KeyNotRegisteredException(k + " is not registered!");
        }
        if (context instanceof ContextWrapper) {
            return ((ContextWrapper)context).put(k, value, false);
        }
        return context.put(k, value);
    }

    @Override
    public <V> V putIfAbsent(@NotNull Key<V> key, V value) {
        return putIfAbsent(key, value, true);
    }

    protected <V> V putIfAbsent(@NotNull Key<V> key, V value, boolean keyCheck) {
        Key<V> k = key;
        Key<V> mappingKey = getMappingKey(key);
        if (mappingKey != null) {
            k = mappingKey;
        }
        if (keyCheck && !keyAvailable(k)) {
            throw new KeyNotRegisteredException(k + " is not registered!");
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
        Key<V> k = key;
        Key<V> mappingKey = getMappingKey(key);
        if (mappingKey != null) {
            k = mappingKey;
        }
        if (keyCheck && !keyAvailable(k)) {
            throw new KeyNotRegisteredException(k + " is not registered!");
        }
        if (context instanceof ContextWrapper) {
            return ((ContextWrapper)context).get(k, false);
        }
        return context.get(k);
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
        if (keyCheck && !keyAvailable(k)) {
            throw new KeyNotRegisteredException(k + " is not registered!");
        }
        if (context instanceof ContextWrapper) {
            return ((ContextWrapper)context).get(k, defaultValue, false);
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
        Key<?> k = key;
        Key<?> mappingKey = getMappingKey(key);
        if (mappingKey != null) {
            k = mappingKey;
        }
        return context.containsKey(k);
    }

    @Override
    public boolean containsValue(Object value) {return context.containsValue(value);}

    @Override
    public <V> V remove(Key<V> key) {
        return remove(key, true);
    }

    protected <V> V remove(Key<V> key, boolean keyCheck) {
        Key<V> k = key;
        Key<V> mappingKey = getMappingKey(key);
        if (mappingKey != null) {
            k = mappingKey;
        }
        if (keyCheck && !keyAvailable(k)) {
            throw new KeyNotRegisteredException(k + " is not registered!");
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
    public boolean keyAvailable(Key<?> key) {
        if (requiredKeys == null || requiredKeys.isEmpty()) {
            return false;
        }
        return requiredKeys.contains(key);
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

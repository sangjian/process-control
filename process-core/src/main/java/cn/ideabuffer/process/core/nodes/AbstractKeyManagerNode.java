package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.KeyManager;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.context.KeyMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AbstractKeyManagerNode extends AbstractNode implements KeyManager {

    private KeyMapper mapper;
    private Set<Key<?>> readableKeys = new HashSet<>();
    private Set<Key<?>> writableKeys = new HashSet<>();

    public AbstractKeyManagerNode() {
        this(null, null, null);
    }

    public AbstractKeyManagerNode(KeyMapper mapper, Set<Key<?>> readableKeys, Set<Key<?>> writableKeys) {
        this.mapper = mapper;
        if (readableKeys != null) {
            this.readableKeys = readableKeys;
        }
        if (writableKeys != null) {
            this.writableKeys = writableKeys;
        }
    }

    @Override
    public KeyMapper getKeyMapper() {
        return this.mapper;
    }

    @Override
    public void setKeyMapper(KeyMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Set<Key<?>> getReadableKeys() {
        return Collections.unmodifiableSet(readableKeys);
    }

    @Override
    public void setReadableKeys(Set<Key<?>> keys) {
        if (keys == null || keys.isEmpty()) {
            return;
        }
        this.readableKeys = keys;
    }

    @Override
    public Set<Key<?>> getWritableKeys() {
        return Collections.unmodifiableSet(writableKeys);
    }

    @Override
    public void setWritableKeys(Set<Key<?>> keys) {
        if (keys == null || keys.isEmpty()) {
            return;
        }
        this.writableKeys = keys;
    }

    protected void addReadableKeys(Set<Key<?>> keys) {
        if (keys == null || keys.isEmpty()) {
            return;
        }
        this.readableKeys.addAll(keys);
    }

    protected void addWritableKeys(Set<Key<?>> keys) {
        if (keys == null || keys.isEmpty()) {
            return;
        }
        this.writableKeys.addAll(keys);
    }

    protected void addReadableKeys(Key<?>... keys) {
        if (keys == null || keys.length == 0) {
            return;
        }
        this.readableKeys.addAll(Arrays.asList(keys));
    }

    protected void addWritableKeys(Key<?>... keys) {
        if (keys == null || keys.length == 0) {
            return;
        }
        this.writableKeys.addAll(Arrays.asList(keys));
    }
}

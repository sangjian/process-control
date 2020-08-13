package cn.ideabuffer.process.core.context;

import cn.ideabuffer.process.core.ProcessDefinition;
import cn.ideabuffer.process.core.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

/**
 * @author sangjian.sj
 * @date 2020/02/07
 */
public class ContextImpl extends ParameterImpl implements Context {

    private Block block;

    private Key<?> resultKey;

    public ContextImpl() {
        this(null, null);
    }

    public ContextImpl(Block block) {
        this(block, null);
    }

    public ContextImpl(Block block, Map<Key<?>, Object> params) {
        super(params);
        this.block = block == null ? new ContextViewBlock() : block;
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public Context cloneContext() {
        return new ContextImpl(null, this.getParams());
    }

    @Override
    public void setResultKey(@NotNull ProcessDefinition<?> definition) {
        this.resultKey = definition.getResultKey();
    }

    @Override
    public <V> Key<V> getResultKey() {
        return (Key<V>)this.resultKey;
    }

    @Override
    public boolean keyAvailable(Key<?> key) {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        ContextImpl that = (ContextImpl)o;
        return Objects.equals(block, that.block);
    }

    @Override
    public int hashCode() {

        return Objects.hash(block);
    }

    private final class ContextViewBlock implements Block {
        private Context context = ContextImpl.this;

        @Override
        public <V> V put(@NotNull Key<V> key, V value) {
            return context.put(key, value);
        }

        @Override
        public <V> V putIfAbsent(@NotNull Key<V> key, V value) {
            return context.putIfAbsent(key, value);
        }

        @Override
        public <V> V get(@NotNull Key<V> key) {
            return context.get(key);
        }

        @Override
        public <V> V get(@NotNull Key<V> key, V defaultValue) {
            return context.get(key, defaultValue);
        }

        @Override
        public Map<Key<?>, Object> getParams() {
            return context.getParams();
        }

        @Override
        public int size() {
            return context.size();
        }

        @Override
        public boolean isEmpty() {
            return context.isEmpty();
        }

        @Override
        public boolean containsKey(Key<?> key) {
            return context.containsKey(key);
        }

        @Override
        public boolean containsValue(Object value) {
            return context.containsValue(value);
        }

        @Override
        public <V> V remove(Key<V> key) {
            return context.remove(key);
        }

        @Override
        public void putAll(@NotNull Map<? extends Key<?>, ?> params) {
            this.context.putAll(params);
        }

        @Override
        public void clear() {
            context.clear();
        }

        @Override
        public boolean allowBreak() {
            return false;
        }

        @Override
        public boolean allowContinue() {
            return false;
        }

        @Override
        public void doBreak() {
            throw new IllegalStateException("break is not allowed in current block");
        }

        @Override
        public void doContinue() {
            throw new IllegalStateException("continue is not allowed in current block");
        }

        @Override
        public boolean hasBroken() {
            return false;
        }

        @Override
        public boolean hasContinued() {
            return false;
        }

        @Override
        public Block getParent() {
            return null;
        }
    }
}

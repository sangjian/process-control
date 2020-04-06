package cn.ideabuffer.process.core.context;

import cn.ideabuffer.process.core.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

/**
 * @author sangjian.sj
 * @date 2020/02/07
 */
public class DefaultContext extends ParameterImpl implements Context {

    private Block block;

    public DefaultContext() {
        this(null, null);
    }

    public DefaultContext(Block block) {
        this(block, null);
    }

    public DefaultContext(Block block, Map<Key<?>, Object> params) {
        super(params);
        this.block = block == null ? new ContextViewBlock() : block;
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        DefaultContext that = (DefaultContext)o;
        return Objects.equals(block, that.block);
    }

    @Override
    public int hashCode() {

        return Objects.hash(block);
    }

    private final class ContextViewBlock extends Block {
        private Context context = DefaultContext.this;

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
    }
}

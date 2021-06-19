package cn.ideabuffer.process.core.context;

import cn.ideabuffer.process.core.block.Block;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;

/**
 * @author sangjian.sj
 * @date 2020/03/26
 */
public class Contexts {

    private Contexts() {
        throw new IllegalStateException("Utility class");
    }

    public static Context newContext() {
        return new ContextImpl();
    }

    public static <V> Key<V> newKey(@NotNull Object key, @NotNull Class<? super V> valueType) {
        return newKey(key, valueType, null);
    }

    public static <V> Key<V> newKey(@NotNull Object key, @NotNull Class<? super V> valueType, String description) {
        return new Key<>(key, valueType, description);
    }

    public static Context clone(@NotNull Context context) {
        return context.cloneContext();
    }

    public static Context of(@NotNull Map<Key<?>, Object> map) {
        return new ContextImpl(null, map);
    }

    public static Context of(@NotNull Block block) {
        return new ContextImpl(block);
    }

    public static Context of(@NotNull Block block, @NotNull Map<Key<?>, Object> map) {
        return new ContextImpl(block, map);
    }

    public static ContextWrapper wrap(@NotNull Context context, @NotNull Block block) {
        return wrap(context, block, null);
    }

    public static ContextWrapper wrap(@NotNull Context context, @NotNull Block block, KeyMapper mapper) {
        return wrap(context, block, mapper, null, null);
    }

    public static ContextWrapper wrap(@NotNull Context context, @NotNull Block block, KeyMapper mapper,
        Set<Key<?>> readableKeys, Set<Key<?>> writableKeys) {
        return new ContextWrapper(context, block, mapper, readableKeys, writableKeys);
    }

    public static ContextWrapper wrap(@NotNull Context context, @NotNull ExecutableNode<?, ?> node) {
        return wrap(context, context.getBlock(), node.getKeyMapper(), node.getReadableKeys(), node.getWritableKeys());
    }
}

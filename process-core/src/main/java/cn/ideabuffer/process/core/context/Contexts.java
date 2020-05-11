package cn.ideabuffer.process.core.context;

import cn.ideabuffer.process.core.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

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

    public static <V> Key<V> newKey(@NotNull Object key, @NotNull Class<V> valueType) {
        return new Key<>(key, valueType);
    }

    public static Context clone(@NotNull Context context) {
        return new ContextImpl(null, context.getParams());
    }

    public static Context cloneWithBlock(@NotNull Context context) {
        return new ContextImpl(context.getBlock(), context.getParams());
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
        return new ContextWrapper(context, block);
    }
}

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
        return new DefaultContext();
    }

    public static <V> Key<V> newKey(@NotNull Object key, @NotNull Class<V> valueType) {
        return new Key<>(key, valueType);
    }

    public static Context clone(@NotNull Context context) {
        return new DefaultContext(null, context.getParams());
    }

    public static Context cloneWithBlock(@NotNull Context context) {
        return new DefaultContext(context.getBlock(), context.getParams());
    }

    public static Context of(@NotNull Map<Key<?>, Object> map) {
        return new DefaultContext(null, map);
    }

    public static Context of(@NotNull Block block) {
        return new DefaultContext(block);
    }

    public static Context of(@NotNull Block block, @NotNull Map<Key<?>, Object> map) {
        return new DefaultContext(block, map);
    }

    public static ContextWrapper wrap(@NotNull Context context, @NotNull Block block) {
        return new ContextWrapper(context, block);
    }
}

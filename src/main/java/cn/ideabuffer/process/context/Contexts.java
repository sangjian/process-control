package cn.ideabuffer.process.context;

import cn.ideabuffer.process.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * @author sangjian.sj
 * @date 2020/03/26
 */
public class Contexts {

    public static Context newContext() {
        return new DefaultContext();
    }

    public static <V> ContextKey<V> newKey(@NotNull Object key, @NotNull Class<V> valueType) {
        return new ContextKey<>(key, valueType);
    }

    public static Context clone(@NotNull Context context) {
        return new DefaultContext(null, context.getParams());
    }

    public static Context cloneWithBlock(@NotNull Context context) {
        return new DefaultContext(context.getBlock(), context.getParams());
    }

    public static Context of(@NotNull Map<ContextKey<?>, Object> map) {
        return new DefaultContext(null, map);
    }

    public static Context of(@NotNull Block block) {
        return new DefaultContext(block);
    }

    public static Context of(@NotNull Block block, @NotNull Map<ContextKey<?>, Object> map) {
        return new DefaultContext(block, map);
    }

    public static ContextWrapper wrap(@NotNull Context context, @NotNull Block block) {
        return new ContextWrapper(context, block);
    }
}

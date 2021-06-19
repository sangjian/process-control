package cn.ideabuffer.process.core.context;

import cn.ideabuffer.process.core.block.Block;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * 流程上下文工具类
 *
 * @author sangjian.sj
 * @date 2020/03/26
 */
public class Contexts {

    private Contexts() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 创建流程上下文。
     *
     * @return 新创建的流程上下文
     */
    public static Context newContext() {
        return new ContextImpl();
    }

    /**
     * 创建参数Key。
     *
     * @param key       参数Key
     * @param valueType 参数值{@link Class}类型
     * @param <V>       参数value类型
     * @return 新创建的参数Key
     */
    public static <V> Key<V> newKey(@NotNull Serializable key, @NotNull Class<? super V> valueType) {
        return newKey(key, valueType, null);
    }

    /**
     * 创建参数Key。
     *
     * @param key         参数Key
     * @param valueType   参数值{@link Class}类型
     * @param description 参数描述
     * @param <V>         参数value类型
     * @return 新创建的参数Key
     */
    public static <V> Key<V> newKey(@NotNull Serializable key, @NotNull Class<? super V> valueType,
        String description) {
        return new Key<>(key, valueType, description);
    }

    /**
     * 克隆流程上下文。
     *
     * @param context 被克隆的流程上下文
     * @return 克隆后的流程上下文
     */
    public static Context clone(@NotNull Context context) {
        return context.cloneContext();
    }

    /**
     * 根据参数map创建流程上下文。
     *
     * @param map 参数map
     * @return 根据参数map创建的流程上下文
     */
    public static Context of(@NotNull Map<Key<?>, Object> map) {
        return new ContextImpl(null, map);
    }

    /**
     * 根据block创建流程上下文。
     *
     * @param block {@link Block}
     * @return 根据参数map创建的流程上下文
     */
    public static Context of(@NotNull Block block) {
        return new ContextImpl(block);
    }

    /**
     * 根据block和参数map创建流程上下文。
     *
     * @param block {@link Block}
     * @param map   参数map
     * @return 根据block和参数map创建的流程上下文
     */
    public static Context of(@NotNull Block block, @NotNull Map<Key<?>, Object> map) {
        return new ContextImpl(block, map);
    }

    /**
     * 包装新的流程上下文
     *
     * @param context 被包装的context
     * @param block   被包装的block
     * @return 包装后的程上下文
     */
    public static ContextWrapper wrap(@NotNull Context context, @NotNull Block block) {
        return wrap(context, block, null);
    }

    /**
     * 包装新的流程上下文
     *
     * @param context 被包装的context
     * @param block   被包装的block
     * @param mapper  参数映射器
     * @return 包装后的程上下文
     */
    public static ContextWrapper wrap(@NotNull Context context, @NotNull Block block, KeyMapper mapper) {
        return wrap(context, block, mapper, null, null);
    }

    /**
     * 包装新的流程上下文
     *
     * @param context      被包装的context
     * @param block        被包装的block
     * @param mapper       参数映射器
     * @param readableKeys 可读参数set
     * @param writableKeys 可写参数set
     * @return 包装后的程上下文
     */
    public static ContextWrapper wrap(@NotNull Context context, @NotNull Block block, KeyMapper mapper,
        Set<Key<?>> readableKeys, Set<Key<?>> writableKeys) {
        return new ContextWrapper(context, block, mapper, readableKeys, writableKeys);
    }

    /**
     * 包装新的流程上下文
     *
     * @param context 被包装的context
     * @param node    流程节点
     * @return 包装后的程上下文
     * @see Contexts#wrap(Context, Block, KeyMapper, Set, Set)
     */
    public static ContextWrapper wrap(@NotNull Context context, @NotNull ExecutableNode<?, ?> node) {
        return wrap(context, context.getBlock(), node.getKeyMapper(), node.getReadableKeys(), node.getWritableKeys());
    }
}

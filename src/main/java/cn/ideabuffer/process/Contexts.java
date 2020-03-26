package cn.ideabuffer.process;

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

    public static Context clone(@NotNull Context context) {
        return new DefaultContext(context);
    }

    public static Context cloneWithBlock(@NotNull Context context) {
        return new DefaultContext(context.getBlock(), context);
    }

    public static Context of(@NotNull Map<?, ?> map) {
        return new DefaultContext(map);
    }

    public static Context of(@NotNull Block block) {
        return new DefaultContext(block);
    }

    public static Context of(@NotNull Block block, @NotNull Map<?, ?> map) {
        return new DefaultContext(block, map);
    }

    public static ContextWrapper wrap(@NotNull Context context, @NotNull Block block) {
        return new ContextWrapper(context, block);
    }
}

package cn.ideabuffer.process.core;

import cn.ideabuffer.process.core.context.Context;
import org.jetbrains.annotations.Nullable;

/**
 * @author sangjian.sj
 * @date 2020/04/27
 */
@FunctionalInterface
public interface Invokable<R> {

    /**
     * 执行并可返回结果
     *
     * @param context 当前上下文
     * @return 执行结果
     * @throws Exception
     */
    @Nullable
    R invoke(Context context) throws Exception;

}

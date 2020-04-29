package cn.ideabuffer.process.core;

import cn.ideabuffer.process.core.context.Context;

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
    R invoke(Context context) throws Exception;

}

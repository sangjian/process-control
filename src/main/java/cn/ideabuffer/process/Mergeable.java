package cn.ideabuffer.process;

import cn.ideabuffer.process.context.Context;

/**
 * 可合并结果接口
 *
 * @author sangjian.sj
 * @date 2020/03/07
 */
@FunctionalInterface
public interface Mergeable<T> {

    /**
     * 执行并可返回结果
     *
     * @param context 当前上下文
     * @return 执行结果
     * @throws Exception
     */
    T invoke(Context context) throws Exception;

}

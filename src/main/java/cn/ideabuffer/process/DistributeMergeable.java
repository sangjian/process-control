package cn.ideabuffer.process;

import org.jetbrains.annotations.NotNull;

/**
 * 分布式合并结果接口
 *
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface DistributeMergeable<T, R> extends Mergeable<T> {

    R merge(T t, @NotNull R r);

}

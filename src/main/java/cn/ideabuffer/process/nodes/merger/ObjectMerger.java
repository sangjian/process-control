package cn.ideabuffer.process.nodes.merger;

import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/03/27
 */
public interface ObjectMerger<P, R> {

    boolean accept(@NotNull Class<?> type);

    R merge(P obj, R result);

}

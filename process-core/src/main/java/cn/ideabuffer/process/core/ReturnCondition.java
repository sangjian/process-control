package cn.ideabuffer.process.core;

import org.jetbrains.annotations.Nullable;

/**
 * @author sangjian.sj
 * @date 2020/07/27
 */
@FunctionalInterface
public interface ReturnCondition<R> {

    boolean onCondition(@Nullable R result);

}

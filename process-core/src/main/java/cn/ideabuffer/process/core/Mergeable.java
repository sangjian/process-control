package cn.ideabuffer.process.core;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

/**
 * 可合并结果接口
 *
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface Mergeable {

    void timeout(long timeout, @NotNull TimeUnit unit);

    long getTimeout();
}

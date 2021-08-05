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

    /**
     * 设置执行超时时间
     *
     * @param timeout 超时时间
     * @param unit    超时时间单位
     */
    void timeout(long timeout, @NotNull TimeUnit unit);

    /**
     * 获取超时时间，单位：毫秒
     *
     * @return
     */
    long getTimeout();
}

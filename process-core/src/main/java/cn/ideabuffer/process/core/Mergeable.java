package cn.ideabuffer.process.core;

import cn.ideabuffer.process.core.handler.ExceptionHandler;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

/**
 * 可合并结果接口
 *
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface Mergeable {

    void exceptionHandler(ExceptionHandler handler);

    ExceptionHandler getExceptionHandler();

    void timeout(long timeout, @NotNull TimeUnit unit);

    long getTimeout();
}

package cn.ideabuffer.process.core;

import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/04/02
 */
public interface Lifecycle {

    void initialize();

    void destroy();

    @NotNull
    LifecycleState getState();
}

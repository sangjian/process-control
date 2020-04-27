package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.Matchable;
import cn.ideabuffer.process.core.Mergeable;
import cn.ideabuffer.process.core.Node;
import cn.ideabuffer.process.core.handler.ExceptionHandler;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

/**
 * 可合并结果的节点
 *
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface MergeableNode<T> extends Node, Mergeable<T>, Matchable {

    void exceptionHandler(ExceptionHandler handler);

    ExceptionHandler getExceptionHandler();

    void timeout(long timeout, @NotNull TimeUnit unit);

    long getTimeout();
}

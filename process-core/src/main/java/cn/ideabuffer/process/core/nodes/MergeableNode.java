package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.Matchable;
import cn.ideabuffer.process.core.Mergeable;
import cn.ideabuffer.process.core.Node;
import cn.ideabuffer.process.core.handler.ExceptionHandler;
import cn.ideabuffer.process.core.rule.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

/**
 * 可合并结果的节点
 *
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface MergeableNode<T> extends Node, Mergeable<T>, Matchable {

    MergeableNode<T> exceptionHandler(ExceptionHandler handler);

    ExceptionHandler getExceptionHandler();

    @Override
    MergeableNode<T> processOn(Rule rule);

    MergeableNode<T> timeout(long timeout, @NotNull TimeUnit unit);

    long getTimeout();
}

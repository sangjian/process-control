package cn.ideabuffer.process.nodes;

import cn.ideabuffer.process.Matchable;
import cn.ideabuffer.process.Mergeable;
import cn.ideabuffer.process.Node;
import cn.ideabuffer.process.handler.ExceptionHandler;
import cn.ideabuffer.process.rule.Rule;
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

package cn.ideabuffer.process.nodes;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.Matchable;
import cn.ideabuffer.process.Mergeable;
import cn.ideabuffer.process.Node;
import cn.ideabuffer.process.handler.ExceptionHandler;
import cn.ideabuffer.process.rule.Rule;

/**
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface MergeableNode<T> extends Node, Mergeable<T>, Matchable {

    @Override
    T invoke(Context context);

    MergeableNode<T> exceptionHandler(ExceptionHandler handler);

    ExceptionHandler getExceptionHandler();

    @Override
    MergeableNode<T> processOn(Rule rule);
}

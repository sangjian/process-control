package cn.ideabuffer.process.nodes;

import cn.ideabuffer.process.DistributeMergeable;
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
public interface DistributeMergeableNode<T, R> extends MergeableNode<T>, DistributeMergeable<T, R> {

    @Override
    DistributeMergeableNode<T, R> exceptionHandler(ExceptionHandler handler);

    @Override
    DistributeMergeableNode<T, R> processOn(Rule rule);

    @Override
    DistributeMergeableNode<T, R> timeout(long timeout, @NotNull TimeUnit unit);
}

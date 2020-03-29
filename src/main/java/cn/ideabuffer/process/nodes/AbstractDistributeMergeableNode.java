package cn.ideabuffer.process.nodes;

import cn.ideabuffer.process.handler.ExceptionHandler;
import cn.ideabuffer.process.rule.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

/**
 * @author sangjian.sj
 * @date 2020/03/07
 */
public abstract class AbstractDistributeMergeableNode<T, R> extends AbstractMergeableNode<T>
    implements DistributeMergeableNode<T, R> {

    @Override
    public DistributeMergeableNode<T, R> exceptionHandler(ExceptionHandler handler) {
        super.exceptionHandler(handler);
        return this;
    }

    @Override
    public DistributeMergeableNode<T, R> processOn(Rule rule) {
        super.processOn(rule);
        return this;
    }

    @Override
    public DistributeMergeableNode<T, R> timeout(long timeout, @NotNull TimeUnit unit) {
        super.timeout(timeout, unit);
        return this;
    }
}

package cn.ideabuffer.process.core.nodes;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

/**
 * @author sangjian.sj
 * @date 2020/03/07
 */
public abstract class AbstractDistributeMergeableNode<T, R> extends AbstractMergeableNode<T>
    implements DistributeMergeableNode<T, R> {

}

package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.DistributeMergeable;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

/**
 * 可合并结果的节点
 *
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface DistributeMergeableNode<T, R> extends MergeableNode<T>, DistributeMergeable<T, R> {

}

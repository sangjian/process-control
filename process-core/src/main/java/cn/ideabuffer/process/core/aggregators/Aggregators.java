package cn.ideabuffer.process.core.aggregators;

import cn.ideabuffer.process.core.nodes.merger.Merger;
import cn.ideabuffer.process.core.nodes.merger.UnitMerger;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * 聚合器工具类
 *
 * @author sangjian.sj
 * @date 2020/03/09
 */
public class Aggregators {

    private Aggregators() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 创建并行通用聚合器
     *
     * @param executor 指定线程池
     * @param merger   结果合并器
     * @param <I>      输入类型
     * @param <O>      输出类型
     * @return 并行通用聚合器
     * @see GenericAggregator
     * @see Merger
     */
    public static <I, O> GenericAggregator<I, O> newParallelGenericAggregator(@NotNull Executor executor,
        @NotNull Merger<I, O> merger) {
        return newParallelGenericAggregator(executor, merger, 0);
    }

    /**
     * 创建并行通用聚合器
     *
     * @param executor 指定线程池
     * @param merger   结果合并器
     * @param timeout  聚合器并行执行超时时间，单位：毫秒
     * @param <I>      输入类型
     * @param <O>      输出类型
     * @return 并行通用聚合器
     * @see GenericAggregator
     * @see Merger
     */
    public static <I, O> GenericAggregator<I, O> newParallelGenericAggregator(@NotNull Executor executor,
        @NotNull Merger<I, O> merger, long timeout) {
        return newParallelGenericAggregator(executor, merger, timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * 创建并行通用聚合器
     *
     * @param executor 指定线程池
     * @param merger   结果合并器
     * @param timeout  聚合器并行执行超时时间
     * @param unit     超时时间单位
     * @param <I>      输入类型
     * @param <O>      输出类型
     * @return 并行通用聚合器
     * @see GenericAggregator
     * @see Merger
     */
    public static <I, O> GenericAggregator<I, O> newParallelGenericAggregator(@NotNull Executor executor,
        @NotNull Merger<I, O> merger, long timeout, @NotNull TimeUnit unit) {
        return new ParallelGenericAggregator<>(executor, merger, timeout, unit);
    }

    /**
     * 创建并行单元化聚合器
     *
     * @param executor 指定线程池
     * @param merger   结果合并器
     * @param <R>      输入类型/输出类型
     * @return 并行单元化聚合器
     * @see UnitAggregator
     * @see UnitMerger
     */
    public static <R> UnitAggregator<R> newParallelUnitAggregator(@NotNull Executor executor,
        @NotNull UnitMerger<R> merger) {
        return new ParallelUnitAggregator<>(executor, merger);
    }

    /**
     * 创建串行通用聚合器
     *
     * @param merger 结果合并器
     * @param <I>    输入类型
     * @param <O>    输出类型
     * @return 串行通用聚合器
     * @see GenericAggregator
     * @see Merger
     */
    public static <I, O> GenericAggregator<I, O> newSerialGenericAggregator(@NotNull Merger<I, O> merger) {
        return new SerialGenericAggregator<>(merger);
    }

    /**
     * 创建串行单元化聚合器
     *
     * @param merger 结果合并器
     * @param <R>    输入类型/输出类型
     * @return 串行单元化聚合器
     * @see UnitAggregator
     * @see UnitMerger
     */
    public static <R> UnitAggregator<R> newSerialUnitAggregator(@NotNull UnitMerger<R> merger) {
        return new SerialUnitAggregator<>(merger);
    }

    /**
     * 创建并行分布式聚合器
     *
     * @param executor    指定线程池
     * @param resultClass 返回结果类型，注意：必须有无参构造器
     * @param <O>         聚合结果类型
     * @return 并行分布式聚合节点
     * @see DistributeAggregator
     */
    public static <O> DistributeAggregator<O> newParallelDistributeAggregator(
        @NotNull Executor executor,
        @NotNull Class<O> resultClass) {
        return newParallelDistributeAggregator(executor, resultClass, 0);
    }

    /**
     * 创建并行分布式聚合器
     *
     * @param executor    指定线程池
     * @param resultClass 返回结果类型，注意：必须有无参构造器
     * @param timeout     聚合器并行执行超时时间，单位：毫秒
     * @param <O>         聚合结果类型
     * @return 并行分布式聚合节点
     * @see DistributeAggregator
     */
    public static <O> DistributeAggregator<O> newParallelDistributeAggregator(
        @NotNull Executor executor,
        @NotNull Class<O> resultClass, long timeout) {
        return newParallelDistributeAggregator(executor, resultClass, timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * 创建并行分布式聚合器
     *
     * @param executor    指定线程池
     * @param resultClass 返回结果类型，注意：必须有无参构造器
     * @param timeout     聚合器并行执行超时时间
     * @param unit        超时时间单位
     * @param <O>         聚合结果类型
     * @return 并行分布式聚合节点
     * @see DistributeAggregator
     */
    public static <O> DistributeAggregator<O> newParallelDistributeAggregator(
        @NotNull Executor executor,
        @NotNull Class<O> resultClass, long timeout, @NotNull TimeUnit unit) {
        return new ParallelDistributeAggregator<>(executor, resultClass, timeout, unit);
    }

    /**
     * 创建串行分布式聚合器
     *
     * @param resultClass 返回结果类型，注意：必须有无参构造器
     * @param <O>         聚合结果类型
     * @return 并行分布式聚合节点
     * @see DistributeAggregator
     */
    public static <O> DistributeAggregator<O> newSerialDistributeAggregator(@NotNull Class<O> resultClass) {
        return new SerialDistributeAggregator<>(resultClass);
    }

}

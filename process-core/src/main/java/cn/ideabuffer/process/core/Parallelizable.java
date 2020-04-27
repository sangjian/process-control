package cn.ideabuffer.process.core;

import java.util.concurrent.Executor;

/**
 * 提供并行执行的能力
 *
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface Parallelizable {

    /**
     * 设置为并行执行
     */
    void parallel();

    /**
     * 指定线程池来并行执行
     *
     * @param executor 执行时所在的线程池
     */
    void parallel(Executor executor);

}

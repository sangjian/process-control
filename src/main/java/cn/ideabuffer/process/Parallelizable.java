package cn.ideabuffer.process;

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
     *
     * @return 当前对象
     */
    Parallelizable parallel();

    /**
     * 指定线程池来并行执行
     *
     * @param executor 执行时所在的线程池
     * @return 当前对象
     */
    Parallelizable parallel(Executor executor);

}

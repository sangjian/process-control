package cn.ideabuffer.process;

import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface Parallelizable {

    Parallelizable parallel();

    Parallelizable parallel(Executor executor);

}

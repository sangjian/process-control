package cn.ideabuffer.process.extension.retry;

import com.github.rholder.retry.Retryer;

/**
 * @author sangjian.sj
 * @date 2020/04/27
 */
public interface Retryable<R> {

    void retryBy(Retryer<R> retryer);

}

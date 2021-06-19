package cn.ideabuffer.process.extension.retry.processors;

import cn.ideabuffer.process.core.Processor;
import com.github.rholder.retry.Retryer;

/**
 * @author sangjian.sj
 * @date 2020/05/06
 */
public interface RetryProcessor<R> extends Processor<R> {

    Retryer<R> getRetryer();

    void setRetryer(Retryer<R> retryer);

    Processor<R> getProcessor();

    void setProcessor(Processor<R> processor);
}

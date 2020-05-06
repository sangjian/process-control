package cn.ideabuffer.process.extension.retry.nodes;

import cn.ideabuffer.process.core.Matchable;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.extension.retry.Retryable;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.extension.retry.processors.RetryProcessor;
import com.github.rholder.retry.Retryer;

/**
 * @author sangjian.sj
 * @date 2020/04/27
 */
public interface RetryableNode<R> extends ExecutableNode<R, Processor<R>>, Matchable, Retryable<R> {

    Retryer<R> getRetryer();

    RetryProcessor<R> getRetryProcessor();

}

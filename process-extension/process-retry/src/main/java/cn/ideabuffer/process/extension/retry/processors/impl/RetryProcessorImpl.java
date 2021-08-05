package cn.ideabuffer.process.extension.retry.processors.impl;

import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.exceptions.ProcessException;
import cn.ideabuffer.process.extension.retry.processors.RetryProcessor;
import com.github.rholder.retry.RetryException;
import com.github.rholder.retry.Retryer;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sangjian.sj
 * @date 2021/06/19
 */
public class RetryProcessorImpl<R> implements RetryProcessor<R>{
    private static final Logger LOGGER = LoggerFactory.getLogger(RetryProcessor.class);

    private Retryer<R> retryer;

    private Processor<R> processor;

    public RetryProcessorImpl(Processor<R> processor) {
        this.processor = processor;
    }

    public RetryProcessorImpl(Retryer<R> retryer, Processor<R> processor) {
        this.retryer = retryer;
        this.processor = processor;
    }

    @Override
    public Retryer<R> getRetryer() {
        return retryer;
    }

    @Override
    public void setRetryer(Retryer<R> retryer) {
        this.retryer = retryer;
    }

    @Override
    public Processor<R> getProcessor() {
        return processor;
    }

    @Override
    public void setProcessor(Processor<R> processor) {
        this.processor = processor;
    }

    @Override
    public R process(@NotNull Context context) throws Exception {
        if (retryer == null) {
            return processor.process(context);
        }
        Throwable t = null;
        try {
            return retryer.call(() -> processor.process(context));
        } catch (RetryException r) {
            int failedNum = r.getNumberOfFailedAttempts();
            long cost = r.getLastFailedAttempt().getDelaySinceFirstAttempt();

            if (r.getLastFailedAttempt().hasException()) {
                t = r.getLastFailedAttempt().getExceptionCause();
            }
            LOGGER.error("retry failed {} times, cost:{}ms", failedNum, cost, t);
            throw new ProcessException("retry failed", t);
        } catch (Exception e) {
            LOGGER.error("retry execute error, context:{}", context, e);
            throw e;
        }
    }
}

package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.status.ProcessStatus;
import com.github.rholder.retry.Attempt;
import com.github.rholder.retry.RetryException;
import com.github.rholder.retry.Retryer;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sangjian.sj
 * @date 2020/04/27
 */
public abstract class AbstractRetrieableNode<R> extends AbstractExecutableNode implements RetrieableNode<R>{

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRetrieableNode.class);

    private Retryer<R> retryer;

    @Override
    protected @NotNull ProcessStatus doExecute(Context context) throws Exception {
        Throwable t = null;
        try {
            R result = retryer.call(() -> invoke(context));
            return onComplete(context, result);
        } catch (RetryException r) {
            int failedNum = r.getNumberOfFailedAttempts();
            long cost = r.getLastFailedAttempt().getDelaySinceFirstAttempt();

            if (r.getLastFailedAttempt().hasException()) {
                t = r.getLastFailedAttempt().getExceptionCause();
            }
            LOGGER.error("retry failed {} times, cost:{}ms", failedNum, cost, t);
        } catch (Throwable e) {
            LOGGER.error("retry execute error, context:{}", context, e);
            throw e;
        }

        return onFailure(context, t);
    }
}

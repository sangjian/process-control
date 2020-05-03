package cn.ideabuffer.process.extension.nodes;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.exception.ProcessException;
import cn.ideabuffer.process.core.nodes.AbstractExecutableNode;
import com.github.rholder.retry.RetryException;
import com.github.rholder.retry.Retryer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sangjian.sj
 * @date 2020/04/27
 */
public abstract class AbstractRetrieableNode<R> extends AbstractExecutableNode<R> implements RetrieableNode<R> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRetrieableNode.class);

    private Retryer<R> retryer;

    @Override
    protected R doExecute(Context context) throws Exception {
        Throwable t = null;
        try {
            return retryer.call(() -> invoke(context));
        } catch (RetryException r) {
            int failedNum = r.getNumberOfFailedAttempts();
            long cost = r.getLastFailedAttempt().getDelaySinceFirstAttempt();

            if (r.getLastFailedAttempt().hasException()) {
                t = r.getLastFailedAttempt().getExceptionCause();
            }
            LOGGER.error("retry failed {} times, cost:{}ms", failedNum, cost, t);
            throw new ProcessException("retry failed", t);
        } catch (Throwable e) {
            LOGGER.error("retry execute error, context:{}", context, e);
            throw e;
        }
    }
}

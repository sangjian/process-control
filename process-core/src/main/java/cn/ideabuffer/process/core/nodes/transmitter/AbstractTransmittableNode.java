package cn.ideabuffer.process.core.nodes.transmitter;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.handler.ExceptionHandler;
import cn.ideabuffer.process.core.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.core.nodes.TransmittableNode;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;

import static cn.ideabuffer.process.core.executor.NodeExecutors.DEFAULT_POOL;

/**
 * @author sangjian.sj
 * @date 2020/03/10
 */
public abstract class AbstractTransmittableNode<R> extends AbstractExecutableNode implements TransmittableNode<R> {

    private TransmittableProcessor processor;

    @Override
    public <V> ResultStream<V> thenApply(@NotNull ResultProcessor<V, R> processor) {
        TransmittableProcessor<V> then = new TransmittableProcessor<>(processor);
        this.processor = then;
        return then;
    }

    @Override
    public <V> ResultStream<V> thenApplyAsync(@NotNull ResultProcessor<V, R> processor) {
        TransmittableProcessor<V> then = new TransmittableProcessor<>(processor, true, getExecutor());
        this.processor = then;
        return then;
    }

    @Override
    public ResultStream<Void> thenAccept(@NotNull ResultConsumer<R> consumer) {
        TransmittableProcessor<Void> then = new TransmittableProcessor<>(consumer);
        this.processor = then;
        return then;
    }

    @Override
    public ResultStream<Void> thenAcceptAsync(@NotNull ResultConsumer<R> consumer) {
        TransmittableProcessor<Void> then = new TransmittableProcessor<>(consumer, true, getExecutor());
        this.processor = then;
        return then;
    }

    @NotNull
    @Override
    public ProcessStatus execute(Context context) throws Exception {

        if (!ruleCheck(context)) {
            return ProcessStatus.PROCEED;
        }

        Executor e = getExecutor() == null ? DEFAULT_POOL : getExecutor();

        Runnable task = () -> {
            preExecute(context);
            Exception exp = null;
            try {
                R result = doInvoke(context);
                if (processor != null) {
                    //noinspection unchecked
                    processor.fire(context, result);
                }
            } catch (Exception ex) {
                ExceptionHandler handler = getExceptionHandler();
                if (handler != null) {
                    handler.handle(ex);
                } else {
                    exp = ex;
                    throw new RuntimeException(ex);
                }
            } finally {
                whenComplete(context, exp);
            }
        };

        if (isParallel()) {
            e.execute(task);
        } else {
            task.run();
        }
        return ProcessStatus.PROCEED;
    }

    @NotNull
    @Override
    protected final ProcessStatus doExecute(Context context) throws Exception {
        return ProcessStatus.PROCEED;
    }

    protected abstract R doInvoke(Context context) throws Exception;
}

package cn.ideabuffer.process.nodes.transmitter;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.handler.ExceptionHandler;
import cn.ideabuffer.process.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.nodes.TransmittableNode;
import cn.ideabuffer.process.rule.Rule;
import cn.ideabuffer.process.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;

import static cn.ideabuffer.process.executor.NodeExecutors.DEFAULT_POOL;

/**
 * @author sangjian.sj
 * @date 2020/03/10
 */
public abstract class AbstractTransmittableNode<R> extends AbstractExecutableNode implements TransmittableNode<R> {

    private TransmittableProcessor processor;

    @Override
    public TransmittableNode<R> exceptionHandler(ExceptionHandler handler) {
        super.exceptionHandler(handler);
        return this;
    }

    @Override
    public TransmittableNode<R> processOn(Rule rule) {
        super.processOn(rule);
        return this;
    }

    @Override
    public TransmittableNode<R> parallel() {
        super.parallel();
        return this;
    }

    @Override
    public TransmittableNode<R> parallel(Executor executor) {
        super.parallel(executor);
        return this;
    }

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

    @Override
    public ProcessStatus execute(Context context) throws Exception {

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

    @Override
    protected final ProcessStatus doExecute(Context context) throws Exception {
        return ProcessStatus.PROCEED;
    }

    protected abstract R doInvoke(Context context) throws Exception;
}

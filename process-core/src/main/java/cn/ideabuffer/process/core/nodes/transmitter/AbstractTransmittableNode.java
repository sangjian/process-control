package cn.ideabuffer.process.core.nodes.transmitter;

import cn.ideabuffer.process.core.LifecycleManager;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.exceptions.ProcessException;
import cn.ideabuffer.process.core.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.core.nodes.TransmittableNode;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;
import java.util.function.Function;

import static cn.ideabuffer.process.core.executors.NodeExecutors.DEFAULT_POOL;

/**
 * @author sangjian.sj
 * @date 2020/03/10
 */
public abstract class AbstractTransmittableNode<R, P extends Processor<R>> extends AbstractExecutableNode<R, P>
    implements TransmittableNode<R, P> {

    private TransmittableProcessor transmittableProcessor;

    private Function<Throwable, ? extends R> exceptionFn;

    @Override
    public <V> ResultStream<V> thenApply(@NotNull ResultProcessor<V, ? extends R> processor) {
        TransmittableProcessor<V> then = new TransmittableProcessor<>(processor);
        this.transmittableProcessor = then;
        return then;
    }

    @Override
    public <V> ResultStream<V> thenApplyAsync(@NotNull ResultProcessor<V, ? extends R> processor) {
        TransmittableProcessor<V> then = new TransmittableProcessor<>(processor, true, getExecutor());
        this.transmittableProcessor = then;
        return then;
    }

    @Override
    public ResultStream<Void> thenAccept(@NotNull ResultConsumer<? extends R> consumer) {
        TransmittableProcessor<Void> then = new TransmittableProcessor<>(consumer);
        this.transmittableProcessor = then;
        return then;
    }

    @Override
    public ResultStream<Void> thenAcceptAsync(@NotNull ResultConsumer<? extends R> consumer) {
        TransmittableProcessor<Void> then = new TransmittableProcessor<>(consumer, true, getExecutor());
        this.transmittableProcessor = then;
        return then;
    }

    @Override
    public ResultStream<R> exceptionally(Function<Throwable, ? extends R> fn) {
        this.exceptionFn = fn;
        return this;
    }

    @NotNull
    @Override
    public ProcessStatus execute(Context context) throws Exception {
        if (!enabled()) {
            return ProcessStatus.proceed();
        }
        Context ctx = Contexts.wrap(context, context.getBlock(), getKeyMapper(), getReadableKeys(), getWritableKeys());
        if (getProcessor() == null || !ruleCheck(ctx)) {
            return ProcessStatus.proceed();
        }

        if (isParallel()) {
            doParallelProcess(ctx);
            return ProcessStatus.proceed();
        }
        try {
            R result = null;
            try {
                result = getProcessor().process(ctx);
            } catch (Throwable t) {
                if (exceptionFn != null) {
                    result = exceptionFn.apply(t);
                }
            }
            if (getResultKey() != null) {
                if (result != null) {
                    ctx.put(getResultKey(), result);
                } else {
                    ctx.remove(getResultKey());
                }
            }
            if (transmittableProcessor != null) {
                //noinspection unchecked
                transmittableProcessor.fire(ctx, result);
            }
            notifyListeners(ctx, result, null, true);
        } catch (Throwable t) {
            logger.error("process error, node:{}, context:{}", this, context, t);
            notifyListeners(ctx, null, t, false);
            throw new ProcessException(t);
        }

        return ProcessStatus.proceed();
    }

    private void doParallelProcess(Context context) {
        Executor e = getExecutor() == null ? DEFAULT_POOL : getExecutor();
        e.execute(() -> {
            R result;
            try {
                result = getProcessor().process(context);
                if (transmittableProcessor != null) {
                    //noinspection unchecked
                    transmittableProcessor.fire(context, result);
                }
                notifyListeners(context, result, null, true);
            } catch (Exception ex) {
                logger.error("process error, node:{}, context:{}", this, context, ex);
                notifyListeners(context, null, ex, false);
            }
        });
    }

    @Override
    public void initialize() {
        super.initialize();
        if (transmittableProcessor != null) {
            LifecycleManager.initialize(transmittableProcessor);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        if (transmittableProcessor != null) {
            LifecycleManager.destroy(transmittableProcessor);
        }
    }
}

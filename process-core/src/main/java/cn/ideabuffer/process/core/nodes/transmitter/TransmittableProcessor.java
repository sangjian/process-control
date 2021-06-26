package cn.ideabuffer.process.core.nodes.transmitter;

import cn.ideabuffer.process.core.Lifecycle;
import cn.ideabuffer.process.core.LifecycleManager;
import cn.ideabuffer.process.core.context.Context;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

import static cn.ideabuffer.process.core.executors.NodeExecutors.DEFAULT_POOL;

/**
 * @author sangjian.sj
 * @date 2020/03/10
 */
public class TransmittableProcessor<P> implements ResultStream<P>, Lifecycle {

    private ResultProcessor processor;

    private ResultConsumer consumer;

    private boolean parallel;

    private Executor executor;

    private TransmittableProcessor next;

    public TransmittableProcessor(ResultProcessor processor) {
        this(processor, false, null);
    }

    public TransmittableProcessor(ResultProcessor processor, boolean parallel, Executor executor) {
        this.processor = processor;
        this.parallel = parallel;
        this.executor = executor;
    }

    public TransmittableProcessor(ResultConsumer consumer) {
        this(consumer, false, null);
    }

    public TransmittableProcessor(ResultConsumer consumer, boolean parallel, Executor executor) {
        this.consumer = consumer;
        this.parallel = parallel;
        this.executor = executor;
    }

    @Override
    public <R> TransmittableProcessor<R> thenApply(@NotNull ResultProcessor<R, P> processor) {
        TransmittableProcessor<R> then = new TransmittableProcessor<>(processor);
        this.next = then;
        return then;
    }

    @Override
    public <R> ResultStream<R> thenApplyAsync(@NotNull ResultProcessor<R, P> processor) {
        TransmittableProcessor<R> then = new TransmittableProcessor<>(processor, true, executor);
        this.next = then;
        return then;
    }

    @Override
    public ResultStream<Void> thenAccept(@NotNull ResultConsumer<P> consumer) {
        TransmittableProcessor<Void> then = new TransmittableProcessor<>(consumer);
        this.next = then;
        return then;
    }

    @Override
    public ResultStream<Void> thenAcceptAsync(@NotNull ResultConsumer<P> consumer) {
        TransmittableProcessor<Void> then = new TransmittableProcessor<>(consumer, true, executor);
        this.next = then;
        return then;
    }

    public void fire(Context context, @Nullable P value) {
        if (parallel) {
            Executor e = executor == null ? DEFAULT_POOL : executor;
            e.execute(() -> doFire(context, value));
        } else {
            doFire(context, value);
        }
    }

    @SuppressWarnings("unchecked")
    private void doFire(Context context, @Nullable P value) {
        Object r = value;
        if (processor != null) {
            r = processor.apply(context, value);
        }
        if (consumer != null) {
            consumer.accept(context, value);
        }
        if (next != null) {
            next.fire(context, r);
        }
    }

    @Override
    public void initialize() {
        if (next != null) {
            LifecycleManager.initialize(next);
        }
    }

    @Override
    public void destroy() {
        if (executor instanceof ExecutorService && !((ExecutorService)executor).isShutdown()) {
            ((ExecutorService)executor).shutdown();
        }
        if (next != null) {
            LifecycleManager.destroy(next);
        }
    }
}

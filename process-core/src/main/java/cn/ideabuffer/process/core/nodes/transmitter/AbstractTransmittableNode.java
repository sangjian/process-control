package cn.ideabuffer.process.core.nodes.transmitter;

import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.context.Context;
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
public abstract class AbstractTransmittableNode<R, P extends Processor<R>> extends AbstractExecutableNode<R, P>
    implements TransmittableNode<R, P> {

    private TransmittableProcessor transmittableProcessor;

    @Override
    public <V> ResultStream<V> thenApply(@NotNull ResultProcessor<V, R> processor) {
        TransmittableProcessor<V> then = new TransmittableProcessor<>(processor);
        this.transmittableProcessor = then;
        return then;
    }

    @Override
    public <V> ResultStream<V> thenApplyAsync(@NotNull ResultProcessor<V, R> processor) {
        TransmittableProcessor<V> then = new TransmittableProcessor<>(processor, true, getExecutor());
        this.transmittableProcessor = then;
        return then;
    }

    @Override
    public ResultStream<Void> thenAccept(@NotNull ResultConsumer<R> consumer) {
        TransmittableProcessor<Void> then = new TransmittableProcessor<>(consumer);
        this.transmittableProcessor = then;
        return then;
    }

    @Override
    public ResultStream<Void> thenAcceptAsync(@NotNull ResultConsumer<R> consumer) {
        TransmittableProcessor<Void> then = new TransmittableProcessor<>(consumer, true, getExecutor());
        this.transmittableProcessor = then;
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
            R result;
            try {
                result = getProcessor().process(context);
                if (transmittableProcessor != null) {
                    //noinspection unchecked
                    transmittableProcessor.fire(context, result);
                }
                onComplete(context, result);
                notifyListeners(context, result, null, true);
            } catch (Exception ex) {
                notifyListeners(context, null, ex, false);
                onFailure(context, ex);
                return;
            }
            if (getNodeListener() != null) {
                getNodeListener().onComplete(context, result);
            }

        };

        if (isParallel()) {
            e.execute(task);
        } else {
            task.run();
        }
        return ProcessStatus.PROCEED;
    }

}

package cn.ideabuffer.process.nodes.transmission;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.nodes.aggregate.ResultConsumer;
import cn.ideabuffer.process.nodes.aggregate.ResultPostProcessor;
import cn.ideabuffer.process.nodes.aggregate.ResultProcessor;

/**
 * @author sangjian.sj
 * @date 2020/03/10
 */
public class TransmittableProcessor<P> implements ResultPostProcessor<P> {

    private ResultProcessor processor;

    private ResultConsumer consumer;

    private TransmittableProcessor next;

    public TransmittableProcessor(ResultProcessor processor) {
        this.processor = processor;
    }

    public TransmittableProcessor(ResultConsumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public <R> TransmittableProcessor<R> thenApply(ResultProcessor<R, P> processor) {
        TransmittableProcessor<R> then = new TransmittableProcessor<>(processor);
        this.next = then;
        return then;
    }

    @Override
    public ResultPostProcessor<Void> thenAccept(ResultConsumer<P> consumer) {
        TransmittableProcessor<Void> then = new TransmittableProcessor<>(consumer);
        this.next = then;
        return then;
    }

    @SuppressWarnings("unchecked")
    public void fire(Context context, P value) {
        Object r = value;
        if(processor != null) {
            r = processor.apply(context, value);
        }
        if(consumer != null) {
            consumer.accept(context, value);
        }
        if(next != null) {
            next.fire(context, r);
        }
    }
}

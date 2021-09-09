package cn.ideabuffer.process.core.processors.impl;

import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.processors.DistributeMerger;
import cn.ideabuffer.process.core.processors.DistributeProcessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DistributeProcessorAdaptor<T, R> implements DistributeProcessor<T, R> {

    private final Processor<T> processor;

    private final DistributeMerger<T, R> merger;

    public DistributeProcessorAdaptor(@NotNull Processor<T> processor, @NotNull DistributeMerger<T, R> merger) {
        this.processor = processor;
        this.merger = merger;
    }

    @Override
    public @Nullable T process(@NotNull Context context) throws Exception {
        return processor.process(context);
    }

    @Override
    public R merge(T t, @NotNull R r) {
        return merger.merge(t, r);
    }
}

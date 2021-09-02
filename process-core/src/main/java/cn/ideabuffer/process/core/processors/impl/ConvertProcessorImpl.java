package cn.ideabuffer.process.core.processors.impl;

import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.ResultConverter;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.processors.ConvertProcessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ConvertProcessorImpl<I, O> implements ConvertProcessor<I, O> {

    private Processor<I> processor;
    private ResultConverter<I, O> converter;

    public ConvertProcessorImpl(@NotNull Processor<I> processor, @NotNull ResultConverter<I, O> converter) {
        this.processor = processor;
        this.converter = converter;
    }

    @Override
    public @Nullable O process(@NotNull Context context) throws Exception {
        I result = processor.process(context);
        return converter.convert(context, result);
    }

    @NotNull
    @Override
    public Processor<I> getProcessor() {
        return processor;
    }

    @NotNull
    @Override
    public ResultConverter<I, O> getConverter() {
        return converter;
    }
}

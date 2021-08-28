package cn.ideabuffer.process.core.processors.builder;

import cn.ideabuffer.process.core.Builder;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.ResultConverter;
import cn.ideabuffer.process.core.processors.ConvertProcessor;
import cn.ideabuffer.process.core.processors.impl.DefaultConvertProcessor;
import org.jetbrains.annotations.NotNull;

public class ConvertProcessorBuilder<I, O> implements Builder<ConvertProcessor<I, O>> {

    private Processor<I> processor;
    private ResultConverter<I, O> converter;

    private ConvertProcessorBuilder() {

    }

    public static <I, O> ConvertProcessorBuilder<I, O> newBuilder() {
        return new ConvertProcessorBuilder<>();
    }

    public ConvertProcessorBuilder<I, O> processor(@NotNull Processor<I> processor) {
        this.processor = processor;
        return this;
    }

    public ConvertProcessorBuilder<I, O> converter(@NotNull ResultConverter<I, O> converter) {
        this.converter = converter;
        return this;
    }

    @Override
    public ConvertProcessor<I, O> build() {
        return new DefaultConvertProcessor<>(processor, converter);
    }
}

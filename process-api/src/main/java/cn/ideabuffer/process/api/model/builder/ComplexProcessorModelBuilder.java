package cn.ideabuffer.process.api.model.builder;

import cn.ideabuffer.process.api.model.processor.ComplexProcessorModel;
import cn.ideabuffer.process.core.processors.ComplexProcessor;

/**
 * @author sangjian.sj
 * @date 2020/06/23
 */
public class ComplexProcessorModelBuilder<R extends ComplexProcessor> extends ProcessorModelBuilder<R> {

    @Override
    public ComplexProcessorModel<R> build(R resource) {
        return new ComplexProcessorModel<>(resource);
    }
}

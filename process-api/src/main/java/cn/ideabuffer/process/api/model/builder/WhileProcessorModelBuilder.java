package cn.ideabuffer.process.api.model.builder;

import cn.ideabuffer.process.api.model.processor.WhileProcessorModel;
import cn.ideabuffer.process.core.processors.WhileProcessor;

/**
 * @author sangjian.sj
 * @date 2020/06/23
 */
public class WhileProcessorModelBuilder<R extends WhileProcessor> extends ProcessorModelBuilder<R> {

    @Override
    public WhileProcessorModel<R> build(R resource) {
        return new WhileProcessorModel<>(resource);
    }
}

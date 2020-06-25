package cn.ideabuffer.process.api.model.builder;

import cn.ideabuffer.process.api.model.processor.IfProcessorModel;
import cn.ideabuffer.process.core.processors.IfProcessor;

/**
 * @author sangjian.sj
 * @date 2020/06/23
 */
public class IfProcessorModelBuilder<R extends IfProcessor> extends ProcessorModelBuilder<R> {

    @Override
    public IfProcessorModel<R> build(R resource) {
        return new IfProcessorModel<>(resource);
    }
}

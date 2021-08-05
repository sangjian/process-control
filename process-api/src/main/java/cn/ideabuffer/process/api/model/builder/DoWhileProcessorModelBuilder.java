package cn.ideabuffer.process.api.model.builder;

import cn.ideabuffer.process.api.model.processor.DoWhileProcessorModel;
import cn.ideabuffer.process.core.processors.WhileProcessor;

/**
 * @author sangjian.sj
 * @date 2020/06/23
 */
public class DoWhileProcessorModelBuilder<R extends WhileProcessor> extends WhileProcessorModelBuilder<R> {

    @Override
    public DoWhileProcessorModel<R> build(R resource) {
        return new DoWhileProcessorModel<>(resource);
    }
}

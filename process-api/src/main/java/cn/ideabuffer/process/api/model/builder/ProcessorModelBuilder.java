package cn.ideabuffer.process.api.model.builder;

import cn.ideabuffer.process.api.model.processor.ProcessorModel;
import cn.ideabuffer.process.core.Processor;

/**
 * @author sangjian.sj
 * @date 2020/06/23
 */
public class ProcessorModelBuilder<R extends Processor> extends ModelBuilder<R> {

    @Override
    public ProcessorModel<R> build(R resource) {
        return new ProcessorModel<>(resource);
    }
}

package cn.ideabuffer.process.api.model.builder;

import cn.ideabuffer.process.api.model.processor.BranchProcessorModel;
import cn.ideabuffer.process.api.model.processor.ProcessorModel;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.processors.BranchProcessor;

/**
 * @author sangjian.sj
 * @date 2020/06/23
 */
public class BranchProcessorModelBuilder<R extends BranchProcessor> extends ProcessorModelBuilder<R> {

    @Override
    public BranchProcessorModel<R> build(R resource) {
        return new BranchProcessorModel<>(resource);
    }
}

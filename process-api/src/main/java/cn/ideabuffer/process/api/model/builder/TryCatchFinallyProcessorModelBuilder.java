package cn.ideabuffer.process.api.model.builder;

import cn.ideabuffer.process.api.model.processor.TryCatchFinallyProcessorModel;
import cn.ideabuffer.process.core.processors.TryCatchFinallyProcessor;

/**
 * @author sangjian.sj
 * @date 2020/06/23
 */
public class TryCatchFinallyProcessorModelBuilder<R extends TryCatchFinallyProcessor> extends ProcessorModelBuilder<R> {

    @Override
    public TryCatchFinallyProcessorModel<R> build(R resource) {
        return new TryCatchFinallyProcessorModel<>(resource);
    }
}

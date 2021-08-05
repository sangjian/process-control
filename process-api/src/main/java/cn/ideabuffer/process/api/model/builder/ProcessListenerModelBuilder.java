package cn.ideabuffer.process.api.model.builder;

import cn.ideabuffer.process.api.model.listener.ProcessListenerModel;
import cn.ideabuffer.process.core.ProcessListener;

/**
 * @author sangjian.sj
 * @date 2020/06/23
 */
public class ProcessListenerModelBuilder<R extends ProcessListener> extends ModelBuilder<R> {

    @Override
    public ProcessListenerModel<R> build(R resource) {
        return new ProcessListenerModel<>(resource);
    }
}

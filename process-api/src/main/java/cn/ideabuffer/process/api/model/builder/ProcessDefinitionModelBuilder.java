package cn.ideabuffer.process.api.model.builder;

import cn.ideabuffer.process.api.model.node.ProcessDefinitionModel;
import cn.ideabuffer.process.core.ProcessDefinition;

/**
 * @author sangjian.sj
 * @date 2020/06/23
 */
public class ProcessDefinitionModelBuilder<R extends ProcessDefinition> extends ModelBuilder<R> {

    @Override
    public ProcessDefinitionModel<R> build(R resource) {
        return new ProcessDefinitionModel<>(resource);
    }
}

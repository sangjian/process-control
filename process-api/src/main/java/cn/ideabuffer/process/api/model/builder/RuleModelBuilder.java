package cn.ideabuffer.process.api.model.builder;

import cn.ideabuffer.process.api.model.RuleModel;
import cn.ideabuffer.process.api.model.processor.ProcessorModel;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.rule.Rule;

/**
 * @author sangjian.sj
 * @date 2020/06/23
 */
public class RuleModelBuilder<R extends Rule> extends ModelBuilder<R> {

    @Override
    public RuleModel build(R resource) {
        return new RuleModel(resource);
    }
}

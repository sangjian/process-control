package cn.ideabuffer.process.api.model.builder;

import cn.ideabuffer.process.api.model.RuleModel;
import cn.ideabuffer.process.core.rules.Rule;

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

package cn.ideabuffer.process.api.model;

import cn.ideabuffer.process.core.rule.Rule;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/06/20
 */
public class RuleModel extends Model {
    private static final long serialVersionUID = -2748699570608620180L;

    public RuleModel(@NotNull Rule rule) {
        super(rule);
    }

}

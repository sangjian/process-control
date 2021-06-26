package cn.ideabuffer.process.core.rules;

import cn.ideabuffer.process.core.context.Context;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class Not implements Rule {

    private Rule rule;

    public Not(@NotNull Rule rule) {
        this.rule = rule;
    }

    @Override
    public boolean match(Context context) {
        return !rule.match(context);
    }
}

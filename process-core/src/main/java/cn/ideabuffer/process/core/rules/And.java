package cn.ideabuffer.process.core.rules;

import cn.ideabuffer.process.core.context.Context;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class And implements Rule {

    private Rule[] rules;

    public And(@NotNull Rule... rules) {
        this.rules = rules;
    }

    @Override
    public boolean match(Context context) {
        for (Rule rule : rules) {
            if (!rule.match(context)) {
                return false;
            }
        }
        return true;
    }
}

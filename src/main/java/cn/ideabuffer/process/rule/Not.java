package cn.ideabuffer.process.rule;

import cn.ideabuffer.process.Context;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class Not implements Rule {

    private Rule rule;

    public Not(Rule rule) {
        this.rule = rule;
    }

    @Override
    public boolean match(Context context) {
        return !rule.match(context);
    }
}

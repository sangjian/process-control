package cn.ideabuffer.process.rule;

import cn.ideabuffer.process.Context;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class And implements Rule {

    private Rule[] rules;

    public And(Rule... rules) {
        this.rules = rules;
    }

    @Override
    public boolean match(Context context) {
        for (Rule rule : rules) {
            if(!rule.match(context)) {
                return false;
            }
        }
        return true;
    }
}

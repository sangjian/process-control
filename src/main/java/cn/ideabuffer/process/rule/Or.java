package cn.ideabuffer.process.rule;

import cn.ideabuffer.process.Context;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class Or implements Rule {

    private Rule[] rules;

    public Or(Rule... rules) {
        this.rules = rules;
    }

    @Override
    public boolean match(Context context) {
        for (Rule rule : rules) {
            if(rule.match(context)) {
                return true;
            }
        }
        return false;
    }
}

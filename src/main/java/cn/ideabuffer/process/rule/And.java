package cn.ideabuffer.process.rule;

import cn.ideabuffer.process.Context;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class And implements Rule {

    private Rule r1;

    private Rule r2;

    public And(Rule r1, Rule r2) {
        this.r1 = r1;
        this.r2 = r2;
    }

    @Override
    public boolean match(Context context) {

        return r1.match(context) && r2.match(context);
    }
}

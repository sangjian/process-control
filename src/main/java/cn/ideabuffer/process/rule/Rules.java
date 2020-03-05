package cn.ideabuffer.process.rule;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class Rules {

    public static Rule and(Rule r1, Rule r2) {
        return new And(r1, r2);
    }

    public static Rule or(Rule r1, Rule r2) {
        return new Or(r1, r2);
    }

}

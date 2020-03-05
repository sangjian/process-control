package cn.ideabuffer.process.rule;

import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class Rules {

    @NotNull
    public static Rule and(@NotNull Rule... rules) {
        return new And(rules);
    }

    @NotNull
    public static Rule or(@NotNull Rule... rules) {
        return new Or(rules);
    }

    @NotNull
    public static Rule not(@NotNull Rule rule) {
        return new Not(rule);
    }

}
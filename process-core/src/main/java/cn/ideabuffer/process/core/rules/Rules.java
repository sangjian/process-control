package cn.ideabuffer.process.core.rules;

import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class Rules {

    private Rules() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 多个规则与操作
     *
     * @param rules
     * @return
     */
    @NotNull
    public static Rule and(@NotNull Rule... rules) {
        return new And(rules);
    }

    /**
     * 多个规则或操作
     *
     * @param rules
     * @return
     */
    @NotNull
    public static Rule or(@NotNull Rule... rules) {
        return new Or(rules);
    }

    /**
     * 规则非操作
     *
     * @param rule
     * @return
     */
    @NotNull
    public static Rule not(@NotNull Rule rule) {
        return new Not(rule);
    }

}

package cn.ideabuffer.process.condition;

import cn.ideabuffer.process.rule.Rule;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class Conditions {

    public static IfWhen newIf() {
        return new IfWhen();
    }

    public static WhileWhen newWhile() {
        return new WhileWhen();
    }

    public static DoWhileWhen newDoWhile() {
        return new DoWhileWhen();
    }
}

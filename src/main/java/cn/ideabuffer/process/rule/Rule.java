package cn.ideabuffer.process.rule;

import cn.ideabuffer.process.Context;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
//@FunctionalInterface
public interface Rule {

    boolean match(Context context);

}

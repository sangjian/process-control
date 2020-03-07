package cn.ideabuffer.process;

import cn.ideabuffer.process.rule.Rule;

/**
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface Matchable {

    Matchable processOn(Rule rule);

    Rule getRule();

}

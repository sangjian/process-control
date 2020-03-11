package cn.ideabuffer.process;

import cn.ideabuffer.process.rule.Rule;

/**
 * 可执行规则接口
 *
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface Matchable {

    /**
     * 设置规则
     *
     * @param rule 规则对象
     * @return 当前对象
     */
    Matchable processOn(Rule rule);

    /**
     * 获取规则
     *
     * @return 规则对象
     */
    Rule getRule();

}

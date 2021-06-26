package cn.ideabuffer.process.core.rules;

import cn.ideabuffer.process.core.context.Context;

/**
 * 执行规则
 *
 * @author sangjian.sj
 * @date 2020/03/05
 */
@FunctionalInterface
public interface Rule {

    /**
     * 校验是否满足当前规则
     *
     * @param context 当前流程上下文
     * @return <li>true: 满足规则</li><li>false: 不满足规则</li>
     */
    boolean match(Context context);

}

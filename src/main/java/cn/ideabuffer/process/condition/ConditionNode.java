package cn.ideabuffer.process.condition;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.ExecutableNode;

/**
 * 条件节点
 *
 * @author sangjian.sj
 * @date 2020/01/19
 */
public interface ConditionNode<V> extends ExecutableNode {

    /**
     * 判断执行逻辑
     * @param context 流程上下文
     * @return 判断执行结果
     */
    V judge(Context context);

}

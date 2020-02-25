package cn.ideabuffer.process.condition;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.ExecutableNode;

/**
 * @author sangjian.sj
 * @date 2020/01/19
 */
public interface ConditionNode<V> extends ExecutableNode {

    V judge(Context context);

}

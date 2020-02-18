package cn.ideabuffer.process;


/**
 * @author sangjian.sj
 * @date 2020/01/19
 */
public interface ConditionNode<V> extends ExecutableNode {

    V judge(Context context);

}

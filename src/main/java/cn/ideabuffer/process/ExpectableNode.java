package cn.ideabuffer.process;

/**
 * @author sangjian.sj
 * @date 2020/01/19
 */
public interface ExpectableNode<V> extends ExecutableNode {

    /**
     * 当前节点的预期值
     *
     * @return
     */
    V expectation();

}

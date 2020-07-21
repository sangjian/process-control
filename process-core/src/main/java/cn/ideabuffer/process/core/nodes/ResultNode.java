package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.Processor;

/**
 * @author sangjian.sj
 * @date 2020/07/21
 */
public interface ResultNode<R, P extends Processor<R>> extends ExecutableNode<R, P> {

    void setResultClass(Class<R> resultClass);

    Class<R> getResultClass();

}

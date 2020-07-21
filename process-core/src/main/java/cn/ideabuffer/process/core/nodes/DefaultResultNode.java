package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.Processor;

/**
 * @author sangjian.sj
 * @date 2020/07/21
 */
public class DefaultResultNode<R> extends ProcessNode<R> implements ResultNode<R, Processor<R>> {

    private Class<R> resultClass;

    @Override
    public void setResultClass(Class<R> resultClass) {
        this.resultClass = resultClass;
    }

    @Override
    public Class<R> getResultClass() {
        return resultClass;
    }
}

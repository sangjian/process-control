package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.processors.ResultProcessor;

/**
 * @author sangjian.sj
 * @date 2020/05/22
 */
public class DefaultBaseNode<R> extends AbstractBaseNode<R> {

    public DefaultBaseNode() {
    }

    public DefaultBaseNode(ResultProcessor<R> processor) {
        super(processor);
    }
}

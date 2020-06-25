package cn.ideabuffer.process.api.model.builder;

import cn.ideabuffer.process.api.model.node.TryCatchFinallyNodeModel;
import cn.ideabuffer.process.core.nodes.TryCatchFinallyNode;

/**
 * @author sangjian.sj
 * @date 2020/06/23
 */
public class TryCatchFinallyNodeModelBuilder<R extends TryCatchFinallyNode> extends ExecutableNodeModelBuilder<R> {

    @Override
    public TryCatchFinallyNodeModel<R> build(R resource) {
        return new TryCatchFinallyNodeModel<>(resource);
    }
}

package cn.ideabuffer.process.core.processors;

import cn.ideabuffer.process.core.ResultHandler;
import cn.ideabuffer.process.core.nodes.ExecutableNode;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2021/08/28
 */
public interface NodeGroupProcessor<R> extends ComplexProcessor<R> {

    void addNodes(ExecutableNode<?, ?>... nodes);

    List<ExecutableNode<?, ?>> getNodes();

    ResultHandler<R> getResultHandler();

}

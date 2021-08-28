package cn.ideabuffer.process.core.processors.wrapper.proxy;

import cn.ideabuffer.process.core.ResultHandler;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.processors.BranchProcessor;
import cn.ideabuffer.process.core.processors.NodeGroupProcessor;
import cn.ideabuffer.process.core.processors.wrapper.StatusWrapperHandler;
import cn.ideabuffer.process.core.processors.wrapper.WrapperHandler;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2021/06/17
 */
public class NodeGroupProcessorProxy<R> extends AbstractProcessorProxy<NodeGroupProcessor<R>, R>
    implements NodeGroupProcessor<R> {

    public NodeGroupProcessorProxy(
        @NotNull NodeGroupProcessor<R> target,
        @NotNull WrapperHandler<R> handler) {
        super(target, handler);
    }

    public static <R> NodeGroupProcessor<R> wrap(@NotNull NodeGroupProcessor<R> target, List<WrapperHandler<R>> handlers) {
        if (handlers == null || handlers.isEmpty()) {
            return target;
        }
        NodeGroupProcessor<R> wrapped = target;
        for (int i = handlers.size() - 1; i >= 0; i--) {
            wrapped = new NodeGroupProcessorProxy<>(wrapped, handlers.get(i));
        }
        return wrapped;
    }

    @Override
    public void addNodes(ExecutableNode<?, ?>... nodes) {getTarget().addNodes(nodes);}

    @Override
    public List<ExecutableNode<?, ?>> getNodes() {return getTarget().getNodes();}

    @Override
    public ResultHandler<R> getResultHandler() {
        return getTarget().getResultHandler();
    }
}

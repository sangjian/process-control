package cn.ideabuffer.process.core.processors.wrapper.proxy;

import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.processors.BranchProcessor;
import cn.ideabuffer.process.core.processors.wrapper.WrapperHandler;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2021/06/17
 */
public class BranchProcessorProxy extends AbstractProcessorProxy<BranchProcessor, ProcessStatus>
    implements BranchProcessor {

    public BranchProcessorProxy(
        @NotNull BranchProcessor target,
        @NotNull WrapperHandler<ProcessStatus> handler) {
        super(target, handler);
    }

    public static BranchProcessor wrap(@NotNull BranchProcessor target, List<WrapperHandler<ProcessStatus>> handlers) {
        if (handlers == null || handlers.isEmpty()) {
            return target;
        }
        BranchProcessor wrapped = target;
        for (int i = handlers.size() - 1; i >= 0; i--) {
            wrapped = new BranchProcessorProxy(wrapped, handlers.get(i));
        }
        return wrapped;
    }

    @Override
    public void addNodes(ExecutableNode<?, ?>... nodes) {getTarget().addNodes(nodes);}

    @Override
    public List<ExecutableNode<?, ?>> getNodes() {return getTarget().getNodes();}
}

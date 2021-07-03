package cn.ideabuffer.process.core.processors.wrapper.proxy;

import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.processors.ParallelBranchProcessor;
import cn.ideabuffer.process.core.processors.wrapper.StatusWrapperHandler;
import cn.ideabuffer.process.core.processors.wrapper.WrapperHandler;
import cn.ideabuffer.process.core.status.ProcessStatus;
import cn.ideabuffer.process.core.strategies.ProceedStrategy;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2021/06/17
 */
public class ParallelBranchProcessorProxy extends AbstractProcessorProxy<ParallelBranchProcessor, ProcessStatus>
    implements ParallelBranchProcessor {

    public ParallelBranchProcessorProxy(
        @NotNull ParallelBranchProcessor target,
        @NotNull WrapperHandler<ProcessStatus> handler) {
        super(target, handler);
    }

    public static ParallelBranchProcessor wrap(@NotNull ParallelBranchProcessor target,
        List<StatusWrapperHandler> handlers) {
        if (handlers == null || handlers.isEmpty()) {
            return target;
        }
        ParallelBranchProcessor wrapped = target;
        for (int i = handlers.size() - 1; i >= 0; i--) {
            wrapped = new ParallelBranchProcessorProxy(wrapped, handlers.get(i));
        }
        return wrapped;
    }

    @Override
    public void addBranch(@NotNull ExecutableNode<?, ?>... nodes) {getTarget().addBranch(nodes);}

    @Override
    public void addBranch(@NotNull BranchNode branch) {getTarget().addBranch(branch);}

    @Override
    public void parallelBy(Executor executor) {getTarget().parallelBy(executor);}

    @Override
    public void proceedWhen(@NotNull ProceedStrategy strategy) {getTarget().proceedWhen(strategy);}
}

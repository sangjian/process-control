package cn.ideabuffer.process.core.processors.wrapper.proxy;

import cn.ideabuffer.process.core.nodes.TryCatchFinallyNode;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.processors.TryCatchFinallyProcessor;
import cn.ideabuffer.process.core.processors.wrapper.StatusWrapperHandler;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2021/06/17
 */
public class TryCatchFinallyProcessorProxy extends AbstractProcessorProxy<TryCatchFinallyProcessor, ProcessStatus>
    implements TryCatchFinallyProcessor {

    public TryCatchFinallyProcessorProxy(
        @NotNull TryCatchFinallyProcessor target,
        @NotNull StatusWrapperHandler handler) {
        super(target, handler);
    }

    public static TryCatchFinallyProcessor wrap(@NotNull TryCatchFinallyProcessor target,
        List<StatusWrapperHandler> handlers) {
        if (handlers == null || handlers.isEmpty()) {
            return target;
        }
        TryCatchFinallyProcessor wrapped = target;
        for (int i = handlers.size() - 1; i >= 0; i--) {
            wrapped = new TryCatchFinallyProcessorProxy(wrapped, handlers.get(i));
        }
        return wrapped;
    }

    @Override
    public BranchNode getTryBranch() {return getTarget().getTryBranch();}

    @Override
    public List<TryCatchFinallyNode.CatchMapper> getCatchMapperList() {return getTarget().getCatchMapperList();}

    @Override
    public BranchNode getFinallyBranch() {return getTarget().getFinallyBranch();}
}

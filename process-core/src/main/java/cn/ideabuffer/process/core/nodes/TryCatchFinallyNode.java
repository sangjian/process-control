package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.Lifecycle;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.processors.TryCatchFinallyProcessor;
import cn.ideabuffer.process.core.processors.impl.TryCatchFinallyProcessorImpl;
import cn.ideabuffer.process.core.status.ProcessStatus;

import java.util.Map;
import java.util.Objects;

/**
 * @author sangjian.sj
 * @date 2020/02/26
 */
public class TryCatchFinallyNode extends AbstractExecutableNode<ProcessStatus, TryCatchFinallyProcessor> {

    public TryCatchFinallyNode(BranchNode tryBranch,
        Map<Class<? extends Throwable>, BranchNode> catchMap, BranchNode finallyBranch) {
        super.registerProcessor(new TryCatchFinallyProcessorImpl(tryBranch, catchMap, finallyBranch));
    }

    @Override
    protected final void onDestroy() {
        try {
            if (getProcessor().getTryBranch() != null) {
                getProcessor().getTryBranch().destroy();
            }
            if (getProcessor().getCatchMap() != null) {
                getProcessor().getCatchMap().entrySet().stream().filter(Objects::nonNull).map(Map.Entry::getValue)
                    .filter(Objects::nonNull)
                    .forEach(Lifecycle::destroy);
            }
            if (getProcessor().getFinallyBranch() != null) {
                getProcessor().getFinallyBranch().destroy();
            }
        } catch (Exception e) {
            logger.error("destroy encountered problem!", e);
            throw e;
        }

    }
}

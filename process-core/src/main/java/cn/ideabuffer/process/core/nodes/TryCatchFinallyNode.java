package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.Lifecycle;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.processors.TryCatchFinallyProcessor;
import cn.ideabuffer.process.core.processors.impl.TryCatchFinallyProcessorImpl;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

/**
 * @author sangjian.sj
 * @date 2020/02/26
 */
public class TryCatchFinallyNode extends AbstractExecutableNode<ProcessStatus, TryCatchFinallyProcessor> {

    public TryCatchFinallyNode(BranchNode tryBranch,
        List<CatchMapper> catchMapper, BranchNode finallyBranch) {
        this(new TryCatchFinallyProcessorImpl(tryBranch, catchMapper, finallyBranch));
    }

    public TryCatchFinallyNode(@NotNull TryCatchFinallyProcessor processor) {
        super.registerProcessor(processor);
    }

    @Override
    protected final void onDestroy() {
        try {
            if (getProcessor().getTryBranch() != null) {
                getProcessor().getTryBranch().destroy();
            }
            if (getProcessor().getCatchMapperList() != null) {
                getProcessor().getCatchMapperList().stream().filter(Objects::nonNull).map(CatchMapper::getBranchNode)
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

    public static class CatchMapper {

        private Class<? extends Throwable> exceptionClass;
        private BranchNode branchNode;

        public CatchMapper(Class<? extends Throwable> clazz,
            BranchNode branchNode) {
            this.exceptionClass = clazz;
            this.branchNode = branchNode;
        }

        public Class<? extends Throwable> getExceptionClass() {
            return exceptionClass;
        }

        public BranchNode getBranchNode() {
            return branchNode;
        }
    }
}

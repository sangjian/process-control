package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.processors.TryCatchFinallyProcessor;
import cn.ideabuffer.process.core.processors.impl.TryCatchFinallyProcessorImpl;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

import java.util.List;

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

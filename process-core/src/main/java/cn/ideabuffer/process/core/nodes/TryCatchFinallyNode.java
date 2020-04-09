package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.Lifecycle;
import cn.ideabuffer.process.core.block.Block;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.ContextWrapper;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

/**
 * @author sangjian.sj
 * @date 2020/02/26
 */
public class TryCatchFinallyNode extends AbstractExecutableNode {

    private BranchNode tryBranch;

    private Map<Class<? extends Exception>, BranchNode> catchMap;

    private BranchNode finallyBranch;

    public TryCatchFinallyNode(BranchNode tryBranch,
        Map<Class<? extends Exception>, BranchNode> catchMap, BranchNode finallyBranch) {
        this.tryBranch = tryBranch;
        this.catchMap = catchMap;
        this.finallyBranch = finallyBranch;
    }

    private void preCheck() {
        if (catchMap == null && finallyBranch == null) {
            throw new RuntimeException("'catch' or 'finally' expected");
        }
    }

    public void setTryBranch(BranchNode tryBranch) {
        this.tryBranch = tryBranch;
    }

    public void setCatchMap(
        Map<Class<? extends Exception>, BranchNode> catchMap) {
        this.catchMap = catchMap;
    }

    public void setFinallyBranch(BranchNode finallyBranch) {
        this.finallyBranch = finallyBranch;
    }

    @NotNull
    @Override
    public ProcessStatus doExecute(Context context) throws Exception {

        preCheck();

        try {
            if (tryBranch == null) {
                return ProcessStatus.PROCEED;
            }
            Block tryBlock = new Block(context.getBlock());
            ContextWrapper contextWrapper = Contexts.wrap(context, tryBlock);
            return tryBranch.execute(contextWrapper);
        } catch (Exception e) {
            ProcessStatus status = runCatchBranch(context, e);
            if (ProcessStatus.isComplete(status)) {
                return status;
            }
        } finally {
            runFinallyBranch(context);
        }

        return ProcessStatus.PROCEED;
    }

    private ProcessStatus runCatchBranch(Context context, Exception e) throws Exception {
        if (catchMap == null || catchMap.isEmpty()) {
            return ProcessStatus.PROCEED;
        }
        for (Map.Entry<Class<? extends Exception>, BranchNode> entry : catchMap.entrySet()) {
            Class<? extends Exception> expClass = entry.getKey();
            BranchNode catchBranch = entry.getValue();
            if (expClass.isAssignableFrom(e.getClass())) {
                if (catchBranch == null) {
                    continue;
                }
                Block catchBlock = new Block(context.getBlock());
                ContextWrapper contextWrapper = Contexts.wrap(context, catchBlock);
                ProcessStatus status = catchBranch.execute(contextWrapper);
                if (ProcessStatus.isComplete(status)) {
                    return status;
                }
            }
        }
        return ProcessStatus.PROCEED;
    }

    private void runFinallyBranch(Context context) throws Exception {
        if (finallyBranch == null) {
            return;
        }
        Block finallyBlock = new Block(context.getBlock());
        ContextWrapper contextWrapper = Contexts.wrap(context, finallyBlock);
        finallyBranch.execute(contextWrapper);
    }

    @Override
    protected final void onDestroy() {
        try {
            if (tryBranch != null) {
                tryBranch.destroy();
            }
            if (catchMap != null) {
                catchMap.entrySet().stream().filter(Objects::nonNull).map(Map.Entry::getValue).filter(Objects::nonNull)
                    .forEach(Lifecycle::destroy);
            }
            if (finallyBranch != null) {
                finallyBranch.destroy();
            }
        } catch (Exception e) {
            logger.error("destroy encountered problem!", e);
            throw e;
        }

    }
}

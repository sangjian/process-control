package cn.ideabuffer.process.core.processors;

import cn.ideabuffer.process.core.block.Block;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.ContextWrapper;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * @author sangjian.sj
 * @date 2020/05/02
 */
public class DefaultTryCatchFinallyProcessor implements TryCatchFinallyProcessor {

    private BranchNode tryBranch;
    private Map<Class<? extends Throwable>, BranchNode> catchMap;
    private BranchNode finallyBranch;

    public DefaultTryCatchFinallyProcessor(BranchNode tryBranch, Map<Class<? extends Throwable>, BranchNode> catchMap, BranchNode finallyBranch) {
        this.tryBranch = tryBranch;
        this.catchMap = catchMap;
        this.finallyBranch = finallyBranch;
    }

    @Override
    public BranchNode getTryBranch() {
        return tryBranch;
    }

    public void setTryBranch(BranchNode tryBranch) {
        this.tryBranch = tryBranch;
    }

    @Override
    public Map<Class<? extends Throwable>, BranchNode> getCatchMap() {
        return catchMap;
    }

    public void setCatchMap(
        Map<Class<? extends Throwable>, BranchNode> catchMap) {
        this.catchMap = catchMap;
    }

    @Override
    public BranchNode getFinallyBranch() {
        return finallyBranch;
    }

    public void setFinallyBranch(BranchNode finallyBranch) {
        this.finallyBranch = finallyBranch;
    }

    private void preCheck() {
        if ((catchMap == null || catchMap.isEmpty()) && finallyBranch == null) {
            throw new IllegalStateException("'catch' or 'finally' expected");
        }
    }

    @Override
    public ProcessStatus process(@NotNull Context context) throws Exception {
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
        for (Map.Entry<Class<? extends Throwable>, BranchNode> entry : catchMap.entrySet()) {
            Class<? extends Throwable> expClass = entry.getKey();
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
}

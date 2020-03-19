package cn.ideabuffer.process.nodes;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.ContextWrapper;
import cn.ideabuffer.process.block.Block;
import cn.ideabuffer.process.nodes.branch.BranchNode;

import java.util.HashMap;
import java.util.Map;

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
        if (this.catchMap == null) {
            this.catchMap = new HashMap<>(4, 1);
        }
    }

    private void preCheck() {
        if (catchMap.isEmpty() && finallyBranch == null) {
            throw new RuntimeException("'catch' or 'finally' expected");
        }
    }

    @Override
    public boolean doExecute(Context context) throws Exception {

        preCheck();

        try {
            if (tryBranch == null) {
                return false;
            }
            Block tryBlock = new Block(context.getBlock());
            ContextWrapper contextWrapper = new ContextWrapper(context, tryBlock);
            return tryBranch.execute(contextWrapper);
        } catch (Exception e) {
            if (runCatchBranch(context, e)) {
                return true;
            }
        } finally {
            runFinallyBranch(context);
        }

        return false;
    }

    private boolean runCatchBranch(Context context, Exception e) throws Exception {
        if (catchMap.isEmpty()) {
            return false;
        }
        for (Map.Entry<Class<? extends Exception>, BranchNode> entry : catchMap.entrySet()) {
            Class<? extends Exception> expClass = entry.getKey();
            BranchNode catchBranch = entry.getValue();
            if (expClass.isAssignableFrom(e.getClass())) {
                if (catchBranch == null) {
                    continue;
                }
                Block catchBlock = new Block(context.getBlock());
                ContextWrapper contextWrapper = new ContextWrapper(context, catchBlock);
                if (catchBranch.execute(contextWrapper)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void runFinallyBranch(Context context) throws Exception {
        if (finallyBranch == null) {
            return;
        }
        Block finallyBlock = new Block(context.getBlock());
        ContextWrapper contextWrapper = new ContextWrapper(context, finallyBlock);
        finallyBranch.execute(contextWrapper);
    }
}

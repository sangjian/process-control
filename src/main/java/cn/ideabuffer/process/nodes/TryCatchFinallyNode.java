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

    private Map<Class<? extends Throwable>, BranchNode> catchMap;

    private BranchNode finallyBranch;

    public TryCatchFinallyNode(BranchNode tryBranch,
        Map<Class<? extends Throwable>, BranchNode> catchMap, BranchNode finallyBranch) {
        this.tryBranch = tryBranch;
        this.catchMap = catchMap;
        this.finallyBranch = finallyBranch;
        if(this.catchMap == null) {
            this.catchMap = new HashMap<>(4, 1);
        }
    }

    @Override
    public boolean doExecute(Context context) throws Exception {

        if(catchMap.isEmpty() && finallyBranch == null) {
            throw new RuntimeException("'catch' or 'finally' expected");
        }
        try {
            if(tryBranch == null || tryBranch.getNodes().isEmpty()) {
                return false;
            }
            Block tryBlock = new Block(context.getBlock());
            ContextWrapper contextWrapper = new ContextWrapper(context, tryBlock);
            return tryBranch.execute(contextWrapper);
        } catch (Throwable e) {
            if(!catchMap.isEmpty()) {
                for (Map.Entry<Class<? extends Throwable>, BranchNode> entry : catchMap.entrySet()) {
                    Class<? extends Throwable> expClass = entry.getKey();
                    BranchNode catchBranch = entry.getValue();
                    if(expClass.isAssignableFrom(e.getClass())) {
                        if(catchBranch == null) {
                            continue;
                        }
                        Block catchBlock = new Block(context.getBlock());
                        ContextWrapper contextWrapper = new ContextWrapper(context, catchBlock);
                        if(catchBranch.execute(contextWrapper)) {
                            return true;
                        }
                    }
                }
            }
        } finally {
            if(finallyBranch != null) {
                Block finallyBlock = new Block(context.getBlock());
                ContextWrapper contextWrapper = new ContextWrapper(context, finallyBlock);
                finallyBranch.execute(contextWrapper);
            }
        }

        return false;
    }
}

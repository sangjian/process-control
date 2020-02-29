package cn.ideabuffer.process;


import cn.ideabuffer.process.block.Block;
import cn.ideabuffer.process.block.BlockWrapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author sangjian.sj
 * @date 2020/02/26
 */
public class TryCatchFinallyNode extends AbstractExecutableNode {

    private List<ExecutableNode> tryNodes;

    private List<ExecutableNode> catchNodes;

    private List<ExecutableNode> finallyNodes;

    private Set<Class<? extends Exception>> expSet;

    public TryCatchFinallyNode() {
        this(null);
    }

    public TryCatchFinallyNode(String id) {
        super(id);
        tryNodes = new ArrayList<>();
        catchNodes = new ArrayList<>();
        finallyNodes = new ArrayList<>();
        expSet = new HashSet<>();
    }

    public TryCatchFinallyNode addTryNode(ExecutableNode node) {
        tryNodes.add(node);
        return this;
    }

    public TryCatchFinallyNode addCatchNode(ExecutableNode node) {
        catchNodes.add(node);
        return this;
    }

    public TryCatchFinallyNode addFinallyNode(ExecutableNode node) {
        finallyNodes.add(node);
        return this;
    }

    public TryCatchFinallyNode catchOnException(Class<? extends Exception> expClass) {
        expSet.add(expClass);
        return this;
    }

    @Override
    public boolean execute(Context context) throws Exception {
        if(tryNodes == null || tryNodes.isEmpty()) {
            return false;
        }
        if(catchNodes.isEmpty() && finallyNodes.isEmpty()) {
            throw new RuntimeException("'catch' or 'finally' expected");
        }
        try {
            Block tryBlock = new Block(context.getBlock());
            BlockWrapper blockWrapper = new BlockWrapper(tryBlock);
            ContextWrapper contextWrapper = new ContextWrapper(context, tryBlock);
            for (ExecutableNode node : tryNodes) {
                if(node.execute(contextWrapper)) {
                    return true;
                }
                if(blockWrapper.hasBroken() || blockWrapper.hasContinued()) {
                    break;
                }
            }
        } catch (Exception e) {
            boolean matched = expSet.isEmpty();
            for (Class<? extends Exception> c : expSet) {
                if(e.getClass().isAssignableFrom(c)) {
                    matched = true;
                    break;
                }
            }
            if(matched) {
                Block catchBlock = new Block(context.getBlock());
                BlockWrapper blockWrapper = new BlockWrapper(catchBlock);
                ContextWrapper contextWrapper = new ContextWrapper(context, catchBlock);
                for (ExecutableNode node : catchNodes) {
                    if(node.execute(contextWrapper)) {
                        return true;
                    }
                    if(blockWrapper.hasBroken() || blockWrapper.hasContinued()) {
                        break;
                    }
                }
            }
        } finally {
            Block finallyBlock = new Block(context.getBlock());
            BlockWrapper blockWrapper = new BlockWrapper(finallyBlock);
            ContextWrapper contextWrapper = new ContextWrapper(context, finallyBlock);
            for (ExecutableNode node : finallyNodes) {
                if(node.execute(contextWrapper)) {
                    break;
                }
                if(blockWrapper.hasBroken() || blockWrapper.hasContinued()) {
                    break;
                }
            }
        }

        return false;
    }
}

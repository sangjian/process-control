package cn.ideabuffer.process;


import cn.ideabuffer.process.block.Block;
import cn.ideabuffer.process.block.BlockFacade;
import cn.ideabuffer.process.block.BlockWrapper;
import cn.ideabuffer.process.block.DefaultBlock;

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
            Block tryBlock = new DefaultBlock(context.getBlock());
            BlockWrapper blockWrapper = new BlockWrapper(tryBlock);
            ContextWrapper contextWrapper = new ContextWrapper(context, new BlockFacade(blockWrapper));
            for (ExecutableNode node : tryNodes) {
                if(node.execute(contextWrapper)) {
                    return true;
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
                Block catchBlock = new DefaultBlock(context.getBlock());
                BlockWrapper blockWrapper = new BlockWrapper(catchBlock);
                ContextWrapper contextWrapper = new ContextWrapper(context, new BlockFacade(blockWrapper));
                for (ExecutableNode node : catchNodes) {
                    if(node.execute(contextWrapper)) {
                        break;
                    }
                }
            }
        } finally {
            Block finallyBlock = new DefaultBlock(context.getBlock());
            BlockWrapper blockWrapper = new BlockWrapper(finallyBlock);
            ContextWrapper contextWrapper = new ContextWrapper(context, new BlockFacade(blockWrapper));
            for (ExecutableNode node : finallyNodes) {
                if(node.execute(contextWrapper)) {
                    break;
                }
            }
        }

        return false;
    }
}

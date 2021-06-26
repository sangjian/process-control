package cn.ideabuffer.process.core.processors.impl;

import cn.ideabuffer.process.core.LifecycleManager;
import cn.ideabuffer.process.core.block.BlockFacade;
import cn.ideabuffer.process.core.block.InnerBlock;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.ContextWrapper;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.exception.IllegalCatchGrammarException;
import cn.ideabuffer.process.core.nodes.TryCatchFinallyNode;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.processors.TryCatchFinallyProcessor;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author sangjian.sj
 * @date 2020/05/02
 */
public class TryCatchFinallyProcessorImpl implements TryCatchFinallyProcessor {

    private BranchNode tryBranch;
    private List<TryCatchFinallyNode.CatchMapper> catchMapperList;
    private BranchNode finallyBranch;

    public TryCatchFinallyProcessorImpl(BranchNode tryBranch, List<TryCatchFinallyNode.CatchMapper> catchMapperList,
        BranchNode finallyBranch) {
        checkCatchGrammar(catchMapperList);
        this.tryBranch = tryBranch;
        this.catchMapperList = catchMapperList;
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
    public List<TryCatchFinallyNode.CatchMapper> getCatchMapperList() {
        return catchMapperList;
    }

    public void setCatchMapperList(
        List<TryCatchFinallyNode.CatchMapper> catchMapperList) {
        checkCatchGrammar(catchMapperList);
        this.catchMapperList = catchMapperList;
    }

    @Override
    public BranchNode getFinallyBranch() {
        return finallyBranch;
    }

    public void setFinallyBranch(BranchNode finallyBranch) {
        this.finallyBranch = finallyBranch;
    }

    /**
     * 检查是否满足catch语法
     *
     * @param catchMapperList catchMapperList
     */
    private void checkCatchGrammar(List<TryCatchFinallyNode.CatchMapper> catchMapperList) {
        if (catchMapperList == null || catchMapperList.isEmpty()) {
            return;
        }

        TryCatchFinallyNode.CatchMapper lastMapper = null;

        Set<Class<? extends Throwable>> expClassSet = new HashSet<>();
        for (TryCatchFinallyNode.CatchMapper mapper : catchMapperList) {
            if (expClassSet.contains(mapper.getExceptionClass())) {
                throw new IllegalCatchGrammarException(
                    String.format("Exception \"%s\" is duplicated", mapper.getExceptionClass().getName()));
            }
            expClassSet.add(mapper.getExceptionClass());
            if (lastMapper == null) {
                lastMapper = mapper;
                continue;
            }
            Class<? extends Throwable> lastExpClass = lastMapper.getExceptionClass();
            Class<? extends Throwable> currentExpClass = mapper.getExceptionClass();
            // 校验是否满足catch层级关系
            if (lastExpClass.isAssignableFrom(currentExpClass)) {
                throw new IllegalCatchGrammarException(String
                    .format("Exception \"%s\" has already been caught by \"%s\"", currentExpClass.getName(),
                        lastExpClass.getName()));
            }
            lastMapper = mapper;
        }
    }

    @NotNull
    @Override
    public ProcessStatus process(@NotNull Context context) throws Exception {
        try {
            if (tryBranch == null) {
                return ProcessStatus.proceed();
            }
            InnerBlock tryBlock = new InnerBlock(context.getBlock());
            ContextWrapper contextWrapper = Contexts.wrap(context, new BlockFacade(tryBlock));
            return tryBranch.execute(contextWrapper);
        } catch (Exception e) {
            ProcessStatus status = runCatchBranch(context, e);
            if (ProcessStatus.isComplete(status)) {
                return status;
            }
        } finally {
            runFinallyBranch(context);
        }

        return ProcessStatus.proceed();
    }

    private ProcessStatus runCatchBranch(Context context, Exception e) throws Exception {
        context.setCurrentException(e);
        if (catchMapperList == null || catchMapperList.isEmpty()) {
            throw e;
        }
        for (TryCatchFinallyNode.CatchMapper mapper : catchMapperList) {
            Class<? extends Throwable> expClass = mapper.getExceptionClass();
            BranchNode catchBranch = mapper.getBranchNode();

            if (!expClass.isAssignableFrom(e.getClass()) || catchBranch == null) {
                continue;
            }
            InnerBlock catchBlock = new InnerBlock(context.getBlock());
            ContextWrapper contextWrapper = Contexts.wrap(context, new BlockFacade(catchBlock));
            contextWrapper.setCurrentException(e);
            return catchBranch.execute(contextWrapper);
        }
        throw e;
    }

    private void runFinallyBranch(Context context) throws Exception {
        if (finallyBranch == null) {
            return;
        }
        InnerBlock finallyBlock = new InnerBlock(context.getBlock());
        ContextWrapper contextWrapper = Contexts.wrap(context, new BlockFacade(finallyBlock));
        finallyBranch.execute(contextWrapper);
    }

    @Override
    public void initialize() {
        if (tryBranch != null) {
            LifecycleManager.initialize(tryBranch);
        }
        if (catchMapperList != null) {
            for (TryCatchFinallyNode.CatchMapper catchMapper : catchMapperList) {
                if (catchMapper.getBranchNode() != null) {
                    LifecycleManager.initialize(catchMapper.getBranchNode());
                }
            }
        }
        if (finallyBranch != null) {
            LifecycleManager.initialize(finallyBranch);
        }
    }

    @Override
    public void destroy() {
        if (tryBranch != null) {
            LifecycleManager.destroy(tryBranch);
        }
        if (catchMapperList != null) {
            for (TryCatchFinallyNode.CatchMapper catchMapper : catchMapperList) {
                if (catchMapper.getBranchNode() != null) {
                    LifecycleManager.destroy(catchMapper.getBranchNode());
                }
            }
        }
        if (finallyBranch != null) {
            LifecycleManager.destroy(finallyBranch);
        }
    }
}

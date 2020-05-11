package cn.ideabuffer.process.core.processors;

import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * @author sangjian.sj
 * @date 2020/05/02
 */
public interface TryCatchFinallyProcessor extends Processor<ProcessStatus> {

    BranchNode getTryBranch();

    Map<Class<? extends Throwable>, BranchNode> getCatchMap();

    BranchNode getFinallyBranch();

    @NotNull
    @Override
    ProcessStatus process(@NotNull Context context) throws Exception;
}

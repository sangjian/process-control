package cn.ideabuffer.process.core.processors;

import cn.ideabuffer.process.core.nodes.TryCatchFinallyNode;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.status.ProcessStatus;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/05/02
 */
public interface TryCatchFinallyProcessor extends ComplexProcessor<ProcessStatus> {

    BranchNode getTryBranch();

    void setTryBranch(BranchNode tryBranch);

    List<TryCatchFinallyNode.CatchMapper> getCatchMapperList();

    void setCatchMapperList(
        List<TryCatchFinallyNode.CatchMapper> catchMapperList);

    BranchNode getFinallyBranch();

    void setFinallyBranch(BranchNode finallyBranch);
}

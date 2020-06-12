package cn.ideabuffer.process.core.processors;

import cn.ideabuffer.process.core.nodes.TryCatchFinallyNode;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/05/02
 */
public interface TryCatchFinallyProcessor extends StatusProcessor {

    BranchNode getTryBranch();

    List<TryCatchFinallyNode.CatchMapper> getCatchMapperList();

    BranchNode getFinallyBranch();

}

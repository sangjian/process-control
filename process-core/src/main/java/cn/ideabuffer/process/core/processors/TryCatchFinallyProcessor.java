package cn.ideabuffer.process.core.processors;

import cn.ideabuffer.process.core.nodes.branch.BranchNode;

import java.util.Map;

/**
 * @author sangjian.sj
 * @date 2020/05/02
 */
public interface TryCatchFinallyProcessor extends StatusProcessor {

    BranchNode getTryBranch();

    Map<Class<? extends Throwable>, BranchNode> getCatchMap();

    BranchNode getFinallyBranch();

}

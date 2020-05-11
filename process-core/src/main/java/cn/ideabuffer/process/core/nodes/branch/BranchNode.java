package cn.ideabuffer.process.core.nodes.branch;

import cn.ideabuffer.process.core.Branch;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.processors.BranchProcessor;
import cn.ideabuffer.process.core.status.ProcessStatus;

import java.util.List;

/**
 * 分支节点
 *
 * @author sangjian.sj
 * @date 2020/03/01
 */
public interface BranchNode extends ExecutableNode<ProcessStatus, BranchProcessor>, Branch {

}

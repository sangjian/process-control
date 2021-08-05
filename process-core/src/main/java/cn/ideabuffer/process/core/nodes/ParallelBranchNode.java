package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.processors.ParallelBranchProcessor;
import cn.ideabuffer.process.core.status.ProcessStatus;

/**
 * 可并行执行的分支节点
 *
 * @author sangjian.sj
 * @date 2020/03/01
 */
public interface ParallelBranchNode extends ExecutableNode<ProcessStatus, ParallelBranchProcessor> {

}

package cn.ideabuffer.process.core.processors;

import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.status.ProcessStatus;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/05/02
 */
public interface BranchProcessor extends ComplexProcessor<ProcessStatus> {

    void addNodes(ExecutableNode<?, ?>... nodes);

    List<ExecutableNode<?, ?>> getNodes();

}

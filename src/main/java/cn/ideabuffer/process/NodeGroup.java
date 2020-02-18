package cn.ideabuffer.process;

import cn.ideabuffer.process.ExecutableNode;
import cn.ideabuffer.process.ExecutableNodeGroupBase;

import java.util.concurrent.ExecutorService;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public interface NodeGroup extends ExecutableNode {

    ExecutableNode[] getNodes();

    ExecutableNode getNode(String nodeId);

    ExecutableNodeGroupBase executeFrom(ExecutorService executor);

    ExecutableNodeGroupBase addNode(ExecutableNode node);
}

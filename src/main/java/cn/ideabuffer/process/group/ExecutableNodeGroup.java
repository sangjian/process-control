package cn.ideabuffer.process.group;

import cn.ideabuffer.process.ExecutableNode;
import cn.ideabuffer.process.executor.ExecuteStrategy;

import java.util.concurrent.ExecutorService;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public interface ExecutableNodeGroup extends ExecutableNode {

    ExecutableNode[] getNodes();

    ExecutableNodeGroup addNode(ExecutableNode node);

    @Override
    ExecutableNodeGroup executeOn(ExecutorService executor);

    ExecutableNodeGroup executeOn(ExecutorService executor, ExecuteStrategy strategy);

    ExecuteStrategy getExecuteStrategy();
}

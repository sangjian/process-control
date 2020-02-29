package cn.ideabuffer.process.group;

import cn.ideabuffer.process.ExecutableNode;
import cn.ideabuffer.process.executor.ExecuteStrategy;

import java.util.concurrent.ExecutorService;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public interface ExecutableNodeGroup extends ExecutableNode {

    /**
     * 获取分组节点列表
     * @return
     */
    ExecutableNode[] getNodes();

    /**
     * 增加分组节点
     * @param node
     * @return
     */
    ExecutableNodeGroup addNode(ExecutableNode node);

    @Override
    ExecutableNodeGroup executeOn(ExecutorService executor);

    ExecutableNodeGroup executeOn(ExecutorService executor, ExecuteStrategy strategy);

    ExecuteStrategy getExecuteStrategy();
}

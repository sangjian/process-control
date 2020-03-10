package cn.ideabuffer.process.nodes.branch;

import cn.ideabuffer.process.Branch;
import cn.ideabuffer.process.nodes.ExecutableNode;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/03/01
 */
public interface BranchNode extends ExecutableNode, Branch<ExecutableNode> {

    /**
     * 添加节点
     *
     * @param nodes
     * @return
     */
    @Override
    BranchNode addNodes(@NotNull ExecutableNode... nodes);

    /**
     * 获取分支下的所有节点
     *
     * @return 节点列表
     */
    @Override
    List<ExecutableNode> getNodes();

}

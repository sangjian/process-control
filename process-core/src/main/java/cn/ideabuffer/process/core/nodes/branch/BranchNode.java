package cn.ideabuffer.process.core.nodes.branch;

import cn.ideabuffer.process.core.Branch;
import cn.ideabuffer.process.core.nodes.ExecutableNode;

import java.util.List;

/**
 * 分支节点
 *
 * @author sangjian.sj
 * @date 2020/03/01
 */
public interface BranchNode extends ExecutableNode, Branch<ExecutableNode> {

    /**
     * 获取分支下的所有节点
     *
     * @return 节点列表
     */
    @Override
    List<ExecutableNode> getNodes();

}

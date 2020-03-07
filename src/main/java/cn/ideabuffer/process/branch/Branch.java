package cn.ideabuffer.process.branch;

import cn.ideabuffer.process.ExecutableNode;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/03/01
 */
public interface Branch extends ExecutableNode {

    /**
     * 添加节点
     * @param nodes
     * @return
     */
    Branch addNodes(@NotNull ExecutableNode... nodes);

    /**
     * 获取分支下的所有节点
     * @return 节点列表
     */
    List<ExecutableNode> getNodes();

}

package cn.ideabuffer.process.core.nodes.branch;

import cn.ideabuffer.process.core.Branch;
import cn.ideabuffer.process.core.handler.ExceptionHandler;
import cn.ideabuffer.process.core.rule.Rule;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * 分支节点
 *
 * @author sangjian.sj
 * @date 2020/03/01
 */
public interface BranchNode extends ExecutableNode, Branch<ExecutableNode> {

    /**
     * 添加节点
     *
     * @param nodes 可执行节点
     * @return 当前分支节点
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

    @Override
    BranchNode processOn(Rule rule);

    @Override
    BranchNode parallel();

    @Override
    BranchNode parallel(Executor executor);

    @Override
    BranchNode exceptionHandler(ExceptionHandler handler);
}

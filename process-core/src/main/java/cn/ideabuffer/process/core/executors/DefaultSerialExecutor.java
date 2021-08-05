package cn.ideabuffer.process.core.executors;

import cn.ideabuffer.process.core.block.Block;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/02/25
 */
public class DefaultSerialExecutor implements SerialExecutor {

    @NotNull
    @Override
    public ProcessStatus execute(@NotNull Context context, ExecutableNode<?, ?>... nodes) throws Exception {
        if (nodes == null || nodes.length == 0) {
            return ProcessStatus.proceed();
        }
        Block block = context.getBlock();
        for (ExecutableNode<?, ?> node : nodes) {
            ProcessStatus status = node.execute(context);
            // 如果节点返回了complete，直接返回
            if (ProcessStatus.isComplete(status)) {
                return status;
            }
            // 如果执行过break或continue操作，退出当前循环
            if (block.hasBroken() || block.hasContinued()) {
                break;
            }
        }
        return ProcessStatus.proceed();
    }
}

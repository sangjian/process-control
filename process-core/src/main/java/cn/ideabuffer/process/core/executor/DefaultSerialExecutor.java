package cn.ideabuffer.process.core.executor;

import cn.ideabuffer.process.core.block.BlockWrapper;
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
    public ProcessStatus execute(Context context, ExecutableNode<?, ?>... nodes) throws Exception {
        if (nodes == null || nodes.length == 0) {
            return ProcessStatus.PROCEED;
        }
        BlockWrapper blockWrapper = new BlockWrapper(context.getBlock());
        for (ExecutableNode<?, ?> node : nodes) {
            ProcessStatus status = node.execute(context);
            if (ProcessStatus.isComplete(status)) {
                return status;
            }
            if (blockWrapper.hasBroken() || blockWrapper.hasContinued()) {
                break;
            }
        }
        return ProcessStatus.PROCEED;
    }
}

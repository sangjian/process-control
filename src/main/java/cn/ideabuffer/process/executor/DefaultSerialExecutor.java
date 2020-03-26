package cn.ideabuffer.process.executor;

import cn.ideabuffer.process.block.BlockWrapper;
import cn.ideabuffer.process.context.Context;
import cn.ideabuffer.process.nodes.ExecutableNode;
import cn.ideabuffer.process.status.ProcessStatus;

/**
 * @author sangjian.sj
 * @date 2020/02/25
 */
public class DefaultSerialExecutor implements SerialExecutor {

    @Override
    public ProcessStatus execute(Context context, ExecutableNode... nodes) throws Exception {
        if (nodes == null || nodes.length == 0) {
            return ProcessStatus.PROCEED;
        }
        BlockWrapper blockWrapper = new BlockWrapper(context.getBlock());
        for (ExecutableNode node : nodes) {
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

package cn.ideabuffer.process.executor;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.ExecutableNode;
import cn.ideabuffer.process.block.BlockWrapper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ExecutorService;

/**
 * @author sangjian.sj
 * @date 2020/02/25
 */
public class SerialStrategy implements ExecuteStrategy {

    @Override
    public boolean execute(ExecutorService executor, Context context, ExecutableNode... nodes) throws Exception {
        if(nodes == null || nodes.length == 0) {
            return false;
        }
        BlockWrapper blockWrapper = new BlockWrapper(context.getBlock());
        for (ExecutableNode node : nodes) {
            if(node.execute(context)) {
                return true;
            }
            if(blockWrapper.hasBroken() || blockWrapper.hasContinued()) {
                break;
            }
        }
        return false;
    }
}

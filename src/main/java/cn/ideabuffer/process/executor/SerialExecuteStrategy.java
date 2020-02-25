package cn.ideabuffer.process.executor;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.ExecutableNode;

import java.util.concurrent.ExecutorService;

/**
 * @author sangjian.sj
 * @date 2020/02/25
 */
public class SerialExecuteStrategy implements ExecuteStrategy {

    @Override
    public boolean execute(ExecutorService executor, Context context, ExecutableNode[] nodes) throws Exception {
        if(nodes == null || nodes.length == 0) {
            return false;
        }
        try {
            for (ExecutableNode node : nodes) {
                if(node.execute(context)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            throw e;
        }
    }
}

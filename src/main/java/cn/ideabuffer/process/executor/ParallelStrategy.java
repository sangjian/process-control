package cn.ideabuffer.process.executor;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.ExecutableNode;

import java.util.concurrent.ExecutorService;

/**
 * @author sangjian.sj
 * @date 2020/02/25
 */
public class ParallelStrategy implements ExecuteStrategy{

    @Override
    public boolean execute(ExecutorService executor, Context context, ExecutableNode... nodes) throws Exception {
        if(nodes == null || nodes.length == 0) {
            return false;
        }
        if(executor == null) {
            throw new NullPointerException();
        }
        for (ExecutableNode node : nodes) {
            executor.execute(() -> {
                try {
                    node.execute(context);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
        return false;
    }
}

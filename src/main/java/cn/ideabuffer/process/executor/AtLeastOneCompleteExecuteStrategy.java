package cn.ideabuffer.process.executor;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.ExecutableNode;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Stream;

/**
 * @author sangjian.sj
 * @date 2020/02/25
 */
public class AtLeastOneCompleteExecuteStrategy implements ExecuteStrategy {

    @Override
    public boolean execute(ExecutorService executor, Context context, ExecutableNode[] nodes) throws Exception {
        if(nodes == null || nodes.length == 0) {
            return false;
        }
        if(executor == null) {
            throw new NullPointerException();
        }

        CompletableFuture.anyOf(Stream.of(nodes)
            .map(node -> CompletableFuture.supplyAsync(() -> {
                try {
                    return node.execute(context);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            })).toArray(CompletableFuture[]::new)).get();

        return false;
    }
}

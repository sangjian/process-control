package cn.ideabuffer.process.executor;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.nodes.ExecutableNode;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author sangjian.sj
 * @date 2020/02/25
 */
public class DefaultParallelExecutor implements ParallelExecutor {

    @Override
    public boolean execute(Executor executor, @NotNull ProceedStrategy proceedStrategy, Context context, ExecutableNode... nodes) throws Exception {
        if(nodes == null || nodes.length == 0) {
            return false;
        }
        List<CompletableFuture<Boolean>> futures = Stream.of(nodes).map(node -> {
            if(executor == null) {
                return CompletableFuture.supplyAsync(() -> {
                    try {
                        return node.execute(context);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            } else {
                return CompletableFuture.supplyAsync(() -> {
                    try {
                        return node.execute(context);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }, executor);
            }
        }).collect(Collectors.toList());

        return proceedStrategy.proceed(futures);
    }
}

package cn.ideabuffer.process.core.executor;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.status.ProcessStatus;
import cn.ideabuffer.process.core.strategy.ProceedStrategy;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author sangjian.sj
 * @date 2020/02/25
 */
public class DefaultParallelExecutor implements ParallelExecutor {

    @NotNull
    @Override
    public ProcessStatus execute(Executor executor, @NotNull ProceedStrategy proceedStrategy, @NotNull Context context,
        ExecutableNode<?, ?>... nodes) throws Exception {
        if (nodes == null || nodes.length == 0) {
            return ProcessStatus.PROCEED;
        }
        List<CompletableFuture<ProcessStatus>> futures = Stream.of(nodes).map(node -> {
            Supplier<ProcessStatus> supplier = () -> {
                try {
                    return node.execute(context);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            };
            return executor == null ? CompletableFuture.supplyAsync(supplier) : CompletableFuture.supplyAsync(supplier,
                executor);
        }).collect(Collectors.toList());

        return ProcessStatus.create(proceedStrategy.proceed(futures));
    }
}

package cn.ideabuffer.process.executor;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.ExecutableNode;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author sangjian.sj
 * @date 2020/02/25
 */
public class AllContinuedStrategy implements ExecuteStrategy {
    @Override
    public boolean execute(ExecutorService executor, Context context, ExecutableNode... nodes) throws Exception {
        if(nodes == null || nodes.length == 0) {
            return false;
        }
        if(executor == null) {
            throw new NullPointerException();
        }

        List<Future<Boolean>> continueList = Stream.of(nodes)
            .map(node -> executor.submit(() -> {
                try {
                    return node.execute(context);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            })).filter(future -> {
                try {
                    return Boolean.FALSE.equals(future.get());
                } catch (Exception e) {
                    throw new RuntimeException();
                }
            }).collect(Collectors.toList());



        return nodes.length == continueList.size();
    }
}

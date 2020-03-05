package cn.ideabuffer.process.executor;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.ExecutableNode;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.stream.Stream;

/**
 * @author sangjian.sj
 * @date 2020/02/25
 */
public class AtLeastOneProceededStrategy implements ExecuteStrategy {

    @Override
    public boolean execute(ExecutorService executor, Context context, ExecutableNode... nodes) throws Exception {
        if(nodes == null || nodes.length == 0) {
            return false;
        }
        if(executor == null) {
            throw new NullPointerException();
        }

        Set<CompletableFuture<Boolean>> futureSet = new HashSet<>();
        for (ExecutableNode node : nodes) {
            CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> {
                try {
                    return node.execute(context);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }, executor);
            futureSet.add(future);
        }

        List<CompletableFuture<Boolean>> completeFutureList = new ArrayList<>();

        for (int i = 0; i < nodes.length; i++) {
            CompletableFuture<Object> future = CompletableFuture.anyOf(Stream.of(futureSet).toArray(CompletableFuture[]::new));

            // TODO
        }


        return false;
    }
}

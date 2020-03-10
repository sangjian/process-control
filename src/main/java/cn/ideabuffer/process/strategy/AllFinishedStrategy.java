package cn.ideabuffer.process.strategy;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author sangjian.sj
 * @date 2020/02/25
 */
public class AllFinishedStrategy implements ProceedStrategy {

    @Override
    public boolean proceed(List<CompletableFuture<Boolean>> futures) throws Exception {
        if (futures == null || futures.isEmpty()) {
            return false;
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).get();
        return false;
    }
}

package cn.ideabuffer.process.strategy;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author sangjian.sj
 * @date 2020/02/25
 */
public class AllProceededStrategy implements ProceedStrategy {

    @Override
    public boolean proceed(List<CompletableFuture<Boolean>> futures) throws Exception {
        if(futures == null || futures.isEmpty()) {
            return false;
        }

        for (CompletableFuture<Boolean> future : futures) {
            if(future.get()) {
                return true;
            }
        }
        return false;
    }
}

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
        if (futures == null || futures.isEmpty()) {
            return true;
        }

        for (CompletableFuture<Boolean> future : futures) {
            if (Boolean.TRUE.equals(future.get())) {
                return false;
            }
        }
        return true;
    }
}

package cn.ideabuffer.process.core.strategy;

import cn.ideabuffer.process.core.status.ProcessStatus;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author sangjian.sj
 * @date 2020/02/25
 */
public class AtLeastOneFinishedStrategy implements ProceedStrategy {

    @Override
    public boolean proceed(List<CompletableFuture<ProcessStatus>> futures) throws Exception {
        if (futures == null || futures.isEmpty()) {
            return true;
        }

        CompletableFuture.anyOf(futures.toArray(new CompletableFuture[0])).get();
        return true;
    }
}

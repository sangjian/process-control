package cn.ideabuffer.process.core.strategies;

import cn.ideabuffer.process.core.status.ProcessStatus;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author sangjian.sj
 * @date 2020/02/25
 */
public class AllFinishedStrategy implements ProceedStrategy {

    @Override
    public boolean proceed(List<CompletableFuture<ProcessStatus>> futures) throws Exception {
        if (futures == null || futures.isEmpty()) {
            return true;
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).get();
        return true;
    }
}

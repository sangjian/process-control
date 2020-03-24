package cn.ideabuffer.process.strategy;

import cn.ideabuffer.process.status.ProcessStatus;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author sangjian.sj
 * @date 2020/03/13
 */
public class SubmittedStrategy implements ProceedStrategy {
    @Override
    public boolean proceed(List<CompletableFuture<ProcessStatus>> futures) throws Exception {
        return true;
    }
}

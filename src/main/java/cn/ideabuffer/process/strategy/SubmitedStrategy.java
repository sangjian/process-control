package cn.ideabuffer.process.strategy;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author sangjian.sj
 * @date 2020/03/13
 */
public class SubmitedStrategy implements ProceedStrategy {
    @Override
    public boolean proceed(List<CompletableFuture<Boolean>> futures) throws Exception {
        return false;
    }
}

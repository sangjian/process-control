package cn.ideabuffer.process.strategy;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface ProceedStrategy {

    boolean proceed(List<CompletableFuture<Boolean>> futures) throws Exception;
}

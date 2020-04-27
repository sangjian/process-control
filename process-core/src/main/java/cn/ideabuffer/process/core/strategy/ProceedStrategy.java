package cn.ideabuffer.process.core.strategy;

import cn.ideabuffer.process.core.status.ProcessStatus;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface ProceedStrategy {

    boolean proceed(List<CompletableFuture<ProcessStatus>> futures) throws Exception;
}
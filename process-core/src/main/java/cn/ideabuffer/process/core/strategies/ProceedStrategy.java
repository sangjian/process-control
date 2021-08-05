package cn.ideabuffer.process.core.strategies;

import cn.ideabuffer.process.core.status.ProcessStatus;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 执行策略
 *
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface ProceedStrategy {

    /**
     * 通过futures的执行结果来判断是否需要继续执行后续节点
     *
     * @param futures 执行的futures
     * @return 如果策略通过，返回true，否则返回false
     * @throws Exception
     */
    boolean proceed(List<CompletableFuture<ProcessStatus>> futures) throws Exception;
}
